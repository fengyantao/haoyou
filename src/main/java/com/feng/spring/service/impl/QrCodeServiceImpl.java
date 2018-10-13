/**
 *
 *
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/22 下午9:24
 *
 *
 */
package com.feng.spring.service.impl;

import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.service.impl.LoginServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.feng.spring.entity.ChartVo;
import com.feng.spring.entity.Constans;
import com.feng.spring.entity.Contact;
import com.feng.spring.mapper.ContactMapper;
import com.feng.spring.service.IRedisService;
import com.feng.spring.service.QrCodeService;
import com.feng.spring.utils.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: feng[17316085657@163.com]
 * @date: 2018/9/22 下午9:24
 * @version: V1.0
 * @review: feng[17316085657@163.com]/2018/9/22 下午9:24
 */
@Service
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {

//    private LoginServiceImpl loginService = new LoginServiceImpl();

    @Autowired
    private IRedisService iRedisService;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private ContactMapper contactMapper;

    @Value("${static.path}")
    private String qrPath;

    @Override
    public void getQrImg(String paramId, HttpServletResponse response) {
//        String uuid = loginService.getUuid();

//        Core core = loginService.getCore();
        LoginServiceImpl loginService = new LoginServiceImpl();
        String uuid = iRedisService.get(paramId);

        String coreStr = iRedisService.get(Constans.REDIS_KEY + uuid);

        Core core = JSONObject.parseObject(coreStr, Core.class);

        loginService.setCore(core);

//        iRedisService.setEx(Constans.REDIS_KEY+paramId,3000,JSONObject.toJSONString(core));
//        iRedisService.setEx(paramId,3000,uuid);
        String path = qrPath + File.separator + core.getUuid() + "QR.jpg";
        log.info("===========================图片路径[{}]",path);
        loginService.getQR(path);
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            BufferedInputStream bis = null;
            OutputStream os = null;
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);

            bis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[512];
            response.reset();
            response.setCharacterEncoding("UTF-8");
            //不同类型的文件对应不同的MIME类型
            response.setContentType("image/*");
            //文件以流的方式发送到客户端浏览器
            //response.setHeader("Content-Disposition","attachment; filename=img.jpg");
            //response.setHeader("Content-Disposition", "inline; filename=img.jpg");

            response.setContentLength(bis.available());

            os = response.getOutputStream();
            int n;
            while ((n = bis.read(buffer)) != -1) {
                os.write(buffer, 0, n);
            }
            bis.close();
            os.flush();
            os.close();

            if (file.exists()) {
                file.delete();
            }

            log.info("sessionId[{}]|获取二维码图片|END", paramId);
        } catch (Exception e) {
            log.error("响应二维码图片异常", e);
        }

    }

    @Override
    public String getQrId(String paramId) {
        LoginServiceImpl loginService = new LoginServiceImpl();
        String uuid = loginService.getUuid();

        Core core = loginService.getCore();

        this.setBeanAndIdInRedis(paramId, uuid, core);
        log.info("sessionId[{}]|获取二维码ID|END", paramId);
        return uuid;
    }

    @Override
    public boolean checkLogin(String paramId) {
        LoginServiceImpl loginService = new LoginServiceImpl();
        String uuid = iRedisService.get(paramId);

        String coreStr = iRedisService.get(Constans.REDIS_KEY + uuid);

        if (StringUtils.isBlank(coreStr)) {
            return false;
        }

        Core core = JSONObject.parseObject(coreStr, Core.class);

        loginService.setCore(core);

        boolean login = loginService.login();

        if (login) {
            log.info("sessionId[{}]|微信已经登录|uuid:[{}]", paramId, uuid);
            if (loginService.webWxInit()) {
                log.info("sessionId[{}]|微信初始化完成|uuid:[{}]", paramId, uuid);
                loginService.webWxGetContact();
                log.info("sessionId[{}]|获取微信好友列表|好友数量:[{}]", paramId, core.getContactList().size());
                this.insertByPool(core.getContactList(), core.getUserSelf(), paramId);
                log.info("sessionId[{}]|线程池操作mysql", paramId);
                this.redisMapByPool(core.getContactList(), paramId);
                log.info("sessionId[{}]|线程池操作redis", paramId);
            } else {
                log.error("===============微信初始化异常，uuid:[{}]", uuid);
            }
            this.setBeanAndIdInRedis(paramId, uuid, core);

        }

        log.info("sessionId[{}]|校验微信是否登录,uuid:[{}]|END", paramId, uuid);
        return login;
    }


    @Override
    public synchronized void setBeanAndIdInRedis(String paramId, String uuid, Core core) {

        iRedisService.setEx(paramId, Constans.REDIS_TIME, uuid);
        iRedisService.setEx(Constans.REDIS_KEY + uuid, Constans.REDIS_TIME, JSONObject.toJSONString(core));

    }

    @Override
    public List<ChartVo> getSex(String paramId) {

        String sex = iRedisService.get(paramId + Constans.SEX_KEY);
        if (StringUtils.isNotBlank(sex)) {
            return JSONObject.parseArray(sex, ChartVo.class);
        }
        String uuid = iRedisService.get(paramId + Constans.UUID_KEY);
        List<ChartVo> list = contactMapper.getSex(uuid);

        if (null != list && list.size() > 0) {
            String sexStr = JSONObject.toJSONString(list);
            iRedisService.setEx(paramId + Constans.SEX_KEY, Constans.REDIS_TIME, sexStr);
            return list;
        } else {
            ChartVo vo1 = new ChartVo("男", 0);
            ChartVo vo2 = new ChartVo("女", 0);
            ChartVo vo3 = new ChartVo("未知", 0);
            List<ChartVo> chartVos = new ArrayList<>();
            chartVos.add(vo1);
            chartVos.add(vo2);
            chartVos.add(vo3);
            return chartVos;
        }
    }

    @Override
    public Map<String, Object> getProvince(String paramId) {

        String province = iRedisService.get(paramId + Constans.PROVIENCE_KEY);
        if (StringUtils.isNotBlank(province)) {
            return JSONObject.parseObject(province, Map.class);
        }

        Map<String, Object> result = new HashMap<>();

        String uuid = iRedisService.get(paramId + Constans.UUID_KEY);
        List<ChartVo> chartVoList = contactMapper.getProvince(uuid);

        if (null != chartVoList && chartVoList.size() > 0) {
            ArrayList<String> nameData = new ArrayList<>();
            ArrayList<Integer> valueData = new ArrayList<>();

            for (ChartVo vo : chartVoList) {
                nameData.add(vo.getName());
                valueData.add(vo.getValue());
            }
            result.put("nameData", nameData);
            result.put("valueData", valueData);
            iRedisService.setEx(paramId + Constans.PROVIENCE_KEY, Constans.REDIS_TIME, JSONObject.toJSONString(result));
            return result;
        } else {
            ArrayList<String> nameData = new ArrayList<>();
            ArrayList<Integer> valueData = new ArrayList<>();
            nameData.add("未知");
            valueData.add(0);
            result.put("nameData", nameData);
            result.put("valueData", valueData);
            return result;

        }
    }

    @Override
    public List<ChartVo> getSign(String paramId) {
        String sign = iRedisService.get(paramId + Constans.SIGN_KEY);
        if (StringUtils.isNotBlank(sign)) {
            return JSONObject.parseArray(sign, ChartVo.class);
        }
        String uuid = iRedisService.get(paramId + Constans.UUID_KEY);

        //返回集合
        List<ChartVo> result = new ArrayList<>();

        //查询数据库
        List<Contact> list = contactMapper.getSign(uuid);
        if (null == list || list.size() == 0) {
            result.add(new ChartVo("无数据", 0));
            return result;
        }
        //分词
        Map<String, Integer> map = new HashMap<>();
        String str = "";
        for (Contact vo : list) {
            String signature = vo.getSignature();
            str += signature;
        }

        //特殊字符转换
        Pattern p = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：\"”“’。，、？\\s*]");
        Matcher matcher = p.matcher(str);
        str = matcher.replaceAll("");

        //拆分中文
        Result parse = IndexAnalysis.parse(str);
        List<Term> terms = parse.getTerms();

        for (Term term : terms) {
            String name = term.getName();
            if (map.containsKey(name)) {
                map.put(name, map.get(name) + 1);
            } else {
                map.put(name, 1);
            }
        }

        //排序
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.add(new ChartVo(entry.getKey(), entry.getValue() * 1000));
        }
        Collections.sort(result);
//        result = result.subList(0, 1000);
        iRedisService.setEx(paramId + Constans.SIGN_KEY, Constans.REDIS_TIME, JSONObject.toJSONString(result));
        return result;
    }


    /**
     * 使用线程池操作数据库
     *
     * @param contactList
     * @param userSelf
     */
    public void insertByPool(List<JSONObject> contactList, JSONObject userSelf, String paramId) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String listStr = JSONObject.toJSONString(contactList);
                List<Contact> contacts = JSONObject.parseArray(listStr, Contact.class);


                Contact contact = JSONObject.toJavaObject(userSelf, Contact.class);
                contact.setUuid(UuidUtils.get32Uuid());
                contactMapper.insert(contact);
                iRedisService.setEx(paramId + Constans.UUID_KEY, Constans.REDIS_TIME, contact.getUuid());
                for (Contact c : contacts) {
                    c.setUuid(UuidUtils.get32Uuid());
                    c.setOwername(contact.getUuid());
                    contactMapper.insert(c);
                }
            }
        });
    }

    /**
     * 线程池中操作redis
     *
     * @param contactList
     * @param paramId
     */
    public void redisMapByPool(List<JSONObject> contactList, String paramId) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String listStr = JSONObject.toJSONString(contactList);
                List<Contact> contacts = JSONObject.parseArray(listStr, Contact.class);
                sexMapByThread(contacts, paramId);
                provinceMapByThread(contacts, paramId);
                signMapByThread(contacts, paramId);
            }
        });
    }

    /**
     * 线程池中放入性别比例
     *
     * @param contacts
     * @param paramId
     */
    public void sexMapByThread(List<Contact> contacts, String paramId) {
        Map<String, Integer> sexMap = new HashMap<>();
        for (Contact vo : contacts) {
            String sex = vo.getSex();
            if (StringUtils.equals(sex, "1")) {
                sex = "男";
            } else if (StringUtils.equals(sex, "2")) {
                sex = "女";
            } else {
                sex = "未知";
            }
            if (sexMap.containsKey(sex)) {
                sexMap.put(sex, sexMap.get(sex) + 1);
            } else {
                sexMap.put(sex, 1);
            }
        }

        List<ChartVo> list = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : sexMap.entrySet()) {
            list.add(new ChartVo(entry.getKey(), entry.getValue()));
        }
        if (list.size() > 0) {

            String sexStr = JSONObject.toJSONString(list);
            iRedisService.setEx(paramId + Constans.SEX_KEY, Constans.REDIS_TIME, sexStr);
        }
    }


    /**
     * 线程池中放入省份比例
     *
     * @param contacts
     * @param paramId
     */
    public void provinceMapByThread(List<Contact> contacts, String paramId) {
        Map<String, Integer> proNumMap = new HashMap<>();
        for (Contact vo : contacts) {
            String province = vo.getProvince();
            if (proNumMap.containsKey(province)) {
                proNumMap.put(province, proNumMap.get(province) + 1);
            } else {
                proNumMap.put(province, 1);
            }
        }
        ArrayList<String> nameData = new ArrayList<>();
        ArrayList<Integer> valueData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : proNumMap.entrySet()) {
            nameData.add(entry.getKey());
            valueData.add(entry.getValue());
        }
        Map<String, Object> result = new HashMap<>();
        result.put("nameData", nameData);
        result.put("valueData", valueData);
        iRedisService.setEx(paramId + Constans.PROVIENCE_KEY, Constans.REDIS_TIME, JSONObject.toJSONString(result));
    }

    /**
     * 线程池中放入签名比例
     *
     * @param contacts
     * @param paramId
     */
    public void signMapByThread(List<Contact> contacts, String paramId) {
        String str = "";
        for (Contact vo : contacts) {
            str += vo.getSignature();
        }
        Map<String, Integer> map = new HashMap<>();
        Pattern p = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：\"”“’。，、？\\s*]");
        Matcher matcher = p.matcher(str);
        str = matcher.replaceAll("");

        List<ChartVo> result = new ArrayList<>();
        //拆分中文
        Result parse = IndexAnalysis.parse(str);
        List<Term> terms = parse.getTerms();

        for (Term term : terms) {
            String name = term.getName();
            if (map.containsKey(name)) {
                map.put(name, map.get(name) + 1);
            } else {
                map.put(name, 1);
            }
        }

        //排序
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.add(new ChartVo(entry.getKey(), entry.getValue() * 1000));
        }
        Collections.sort(result);
//        result = result.subList(0, 1000);
        iRedisService.setEx(paramId + Constans.SIGN_KEY, Constans.REDIS_TIME, JSONObject.toJSONString(result));
    }

}
