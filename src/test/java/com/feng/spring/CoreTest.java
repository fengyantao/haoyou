///**
// *
// *
// * @author: feng[17316085657@163.com]
// * @date: 2018/9/23 下午9:13
// *
// *
// */
//package com.feng.spring;
//
//import com.alibaba.fastjson.JSONObject;
//import com.feng.spring.entity.ChartVo;
//import com.feng.spring.entity.Contact;
//import com.feng.spring.mapper.ContactMapper;
//import com.feng.spring.utils.UuidUtils;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.util.List;
//
///**
// *
// * @author: feng[17316085657@163.com]
// * @date: 2018/9/23 下午9:13
// * @version: V1.0
// * @review: feng[17316085657@163.com]/2018/9/23 下午9:13
// */
//public class CoreTest extends ApplicationTests{
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//
//    @Autowired
//    private ContactMapper mapper;
//
//    @Autowired
//    private ContactMapper contactMapper;
//
//
//    public static void main(String[] args) {
//        String s="http://localhost:8080/style.css";
//        boolean b = s.endsWith(".css");
//        System.out.println(b);
//    }
//
//    @Test
//    public void set(){
//        redisTemplate.opsForValue().set("test:set","testValue1");
//    }
//
//
//    @Test
//    public void testDb(){
//
//        String string=" {\n" + "        \"ChatRoomId\": 0,\n" + "        \"Sex\": 1,\n" + "        \"AttrStatus\": 108583,\n" + "        \"Statues\": 0,\n" + "        \"PYQuanPin\": \"xiaoming\",\n" + "        \"EncryChatRoomId\": \"\",\n" + "        \"DisplayName\": \"\",\n" + "        \"VerifyFlag\": 0,\n" + "        \"UniFriend\": 0,\n" + "        \"ContactFlag\": 3,\n" + "        \"UserName\": \"@724ff7bff669554639f2ffb74679690a\",\n" + "        \"MemberList\": [],\n" + "        \"StarFriend\": 0,\n" + "        \"HeadImgUrl\": \"/cgi-bin/mmwebwx-bin/webwxgeticon?seq=657615367&username=@724ff7bff669554639f2ffb74679690a&skey=\",\n" + "        \"AppAccountFlag\": 0,\n" + "        \"MemberCount\": 0,\n" + "        \"RemarkPYInitial\": \"\",\n" + "        \"City\": \"丰台\",\n" + "        \"NickName\": \"小明\",\n" + "        \"Province\": \"北京\",\n" + "        \"SnsFlag\": 49,\n" + "        \"Alias\": \"\",\n" + "        \"KeyWord\": \"Xia\",\n" + "        \"HideInputBarFlag\": 0,\n" + "        \"Signature\": \"\uD83D\uDC32<span class=\\\"emoji emoji1f434\\\"></span><span class=\\\"emoji emoji1f433\\\"></span><span class=\\\"emoji emoji1f47c\\\"></span>\",\n" + "        \"RemarkName\": \"\",\n" + "        \"RemarkPYQuanPin\": \"\",\n" + "        \"Uin\": 0,\n" + "        \"OwnerUin\": 0,\n" + "        \"IsOwner\": 0,\n" + "        \"PYInitial\": \"XM\"\n" + "    }";
//
//        Contact contact = JSONObject.parseObject(string, Contact.class);
//        contact.setUuid(UuidUtils.get32Uuid());
//
//        mapper.insert(contact);
//    }
//
//    @Test
//    public void testSex(){
//        List<ChartVo> sex = mapper.getSex("e66b0a51a25a4911837fc6d3c836e8e0");
//        System.out.println(JSONObject.toJSONString(sex));
//
//    }
//
//    @Test
//    public void testSign(){
////        List<ChartVo> result=new ArrayList<>();
////        List<Contact> list = contactMapper.getSign("a490b0f727a84a6ab381a93b21c98e6c");
////        System.out.println(JSONObject.toJSONString(list));
//////        分词
////        Map<String,Integer> map=new HashMap<>();
////        for (Contact vo:list){
////            String signature = vo.getSignature();
////            List<Word> words = WordSegmenter.segWithStopWords(signature,SegmentationAlgorithm.FullSegmentation);
////            if (words.size()==0){
////
////            System.out.println("========================================"+words.size()+"============================"+signature);
////            }
////            for (Word word:words){
////                if (map.containsKey(word.getText())){
////                    map.put(word.getText(),map.get(word.getText())+1);
////                }else {
////                    map.put(word.getText(),1);
////                }
////            }
////        }
////
////        int num=0;
////        //排序
////        for(Map.Entry<String, Integer> entry :map.entrySet()){
////            result.add(new ChartVo(entry.getKey(),entry.getValue()));
////            num+=entry.getValue();
////        }
////        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+num+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
////        Collections.sort(result);
////        result=result.subList(0,40);
////        System.out.println(JSONObject.toJSONString(result));
//    }
//}
