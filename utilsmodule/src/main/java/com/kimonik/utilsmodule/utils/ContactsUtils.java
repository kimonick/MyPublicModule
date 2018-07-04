package com.kimonik.utilsmodule.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Data;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;


import com.kimonik.utilsmodule.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * * ===============================================================
 * name:             ContactsUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/3/1
 * description：   联系人相关辅助类
 * method:
 * addContacts(Context context, String contactsName, String phoneNumber, @DrawableRes int drawableResId, String mark)
 * 添加通讯录联系人,含姓名,手机号码,头像和备注,采用更新模式,已有时不会重复插入数据库
 * <p>
 * <p>
 * history：  不同的通讯录对数据库的操作可能不同,简单暴力的方式就是视具体情况直接操作数据库
 * <p>
 * Uri uri = Uri.parse("content://com.android.contacts/数据库下的表名");//对相应的表进行数据操作
 * 主要使用表名:
 * content://com.android.contacts/contacts   总表
 * content://com.android.contacts/data   详细数据表
 * content://com.android.contacts/mimetypes    mime类型表,默认有13中类型
 * content://com.android.contacts/raw_contacts   姓名概览表
 * <p>
 * Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID},
 * "display_name=?", new String[]{contactsName}, null);
 * <p>
 * 参数1:uri,要访问的数据库的表名
 * 参数2:要访问的表中的字段名
 * 参数3:筛选条件
 * 参数4:筛选条件?的具体值
 * 参数5:排序方式
 * <p>
 * <p>
 * C:\Users\Administrator\Desktop
 * 参考文章:https://www.cnblogs.com/xiaoxiaoshen/p/5167541.html
 * *==================================================================
 */

public class ContactsUtils {

    private ContactsUtils() {
        throw new UnsupportedOperationException("can't instance!");
    }


    /**
     * 向通讯录中添加联系人,添加姓名,手机号,头像图片
     * Uri uri = Uri.parse("content://com.android.contacts/data");//对data表的所有数据操作
     * ContentValues values = new ContentValues();
     * //更新电话号码
     * values.put("data1", number);
     * resolver.update(uri, values, "mimetype_id=? and raw_contact_id=?", new String[]{"5", rawContactId+""}) ;
     * //更新联系人姓名
     * values.clear();
     * values.put("data1", name);
     * resolver.update(uri, values, "mimetype_id=? and raw_contact_id=?", new String[]{"7", rawContactId+""}) ;
     * //更新email
     * values.clear();
     * values.put("data1", email);
     * resolver.update(uri, values, "mimetype_id=? and raw_contact_id=?", new String[]{"1", rawContactId+""}) ;
     * //更新im
     * values.clear();
     * values.put("data1", im);
     * resolver.update(uri, values, "mimetype_id=? and raw_contact_id=?", new String[]{"2", rawContactId+""}) ;
     * //更新company
     * values.clear();
     * values.put("data1", company);
     * values.put("data3",name);
     * values.put("data4",position);
     * resolver.update(uri, values, "mimetype_id=? and raw_contact_id=?", new String[]{"4", rawContactId+""}) ;
     */
    public static void addContacts(Context context, String contactsName, String phoneNumber, @DrawableRes int drawableResId, String mark) {

        ContentResolver resolver = context.getContentResolver();//获取内容解析器

//        resolver.delete(Uri.parse("content://com.android.contacts/contacts"), null, null);
//        resolver.delete(Uri.parse("content://com.android.contacts/raw_contacts"), null, null);
//        resolver.delete(Uri.parse("content://com.android.contacts/data"),null,null);
//        for (int i = 0; i < 253; i++) {
//            Log.e("TAG", "addContacts: ---shanchucaozuo--"
//                    +resolver.delete(Uri.parse("content://com.android.contacts/data"), null, null));


//        }

        ContentValues values = new ContentValues();//ContentResolver的存储保存类
        long rawContactId = -1;

        boolean existFlag = false;//默认要创建的联系人不存在
        Uri uri = Uri.parse("content://com.android.contacts/data");//对data表的所有数据操作

        if (TextUtils.isEmpty(contactsName)) {
            return;
        } else {
            //查询  raw_contacts
            Uri uri1 = Uri.parse("content://com.android.contacts/raw_contacts");
            Cursor cursor = resolver.query(uri1, new String[]{Data._ID},
                    "display_name=?", new String[]{contactsName}, null);
            try {
                if (cursor!=null)
                Log.e("TAG", "addContacts: -----"+cursor.moveToFirst());

                if (cursor != null && cursor.moveToNext()) {//存疑:首次调用moveToNext()是查询的第一个还是跳过第一个????
//                    while (cursor.moveToNext()) {//while循环中使用moveToFirst会造成死循环
                    //ContactsContract.Contacts._ID属于raw_contacts表,与contacts表中的_ID相同,与data表中的raw_contact_id也相同
                    rawContactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    existFlag = true;
//                    }
                } else {
                    //先创建一个空的联系人,在raw_contacts表中
                    Uri rawContactUri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
                    //获取新建该联系人对象存储的唯一id
                    rawContactId = ContentUris.parseId(rawContactUri);//获得新建空的联系人的ID
                    existFlag = false;
                }
            } catch (Exception e) {
                ToastUtils.showToast(context, R.string.tianjialianxirenshibai);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        if (!existFlag) {
            // 表插入姓名数据
            values.clear();//清空values
            values.put(Data.RAW_CONTACT_ID, rawContactId);//赋值ID
            values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);// 插入的值的mime内容类型
            values.put(StructuredName.GIVEN_NAME, contactsName);//联系人姓名
            resolver.insert(Data.CONTENT_URI, values);//插入联系人姓名
        }


        //写入电话
        values.clear();
        if (existFlag) {
            //更新电话号码,精确插入数据库,与其他通讯录型软件可能存在兼容性问题
            values.put("data1", phoneNumber);
            //vnd.android.cursor.item/phone_v2  mimetypes表中id为5表示的类型
//                模拟器中contacts数据库mimetypes表中所有的字段
//             id               mimetype
//             1	vnd.android.cursor.item/email_v2
//             2	vnd.android.cursor.item/im
//             3	vnd.android.cursor.item/nickname
//             4	vnd.android.cursor.item/organization
//             5	vnd.android.cursor.item/phone_v2
//             6	vnd.android.cursor.item/sip_address
//             7	vnd.android.cursor.item/name
//             8	vnd.android.cursor.item/postal-address_v2
//             9	vnd.android.cursor.item/identity
//             10	vnd.android.cursor.item/photo
//             11	vnd.android.cursor.item/group_membership
//             12	vnd.android.cursor.item/website
//             13	vnd.android.cursor.item/contact_event

            resolver.update(uri, values, "mimetype_id=? and raw_contact_id=?", new String[]{"5", rawContactId + ""});
        } else {
            //使用insert时,在模拟器中是追加主要电话号码,但读取时只能读一个号码
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
            values.put(Phone.NUMBER, phoneNumber);//插入电话号码
            values.put(Phone.TYPE, Phone.TYPE_MOBILE);
            resolver.insert(Data.CONTENT_URI, values);
        }


        //写入头像
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableResId);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        values.clear();
        if (existFlag) {
            values.put("data15", out.toByteArray());
            resolver.update(uri, values, "mimetype_id=? and raw_contact_id=?", new String[]{"10", rawContactId + ""});
        } else {
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, out.toByteArray());
            resolver.insert(Data.CONTENT_URI, values);
        }


        // 表插入备注数据
        values.clear();//清空values
        if (existFlag) {
            values.put("data13", mark);//data13字段值自选,注意不要与其他保存字段重复
            resolver.update(uri, values, "mimetype_id=? and raw_contact_id=?", new String[]{"13", rawContactId + ""});
        } else {
            values.put(Data.RAW_CONTACT_ID, rawContactId);//赋值ID
            values.put(Data.MIMETYPE, Event.CONTENT_ITEM_TYPE);// 插入的值的mime内容类型
            values.put(Event.TYPE, Event.TYPE_OTHER);//备注类型
            values.put(Event.START_DATE, mark);//备注
            resolver.insert(Data.CONTENT_URI, values);//插入联系人姓名
        }
    }

    /**
     * 读取通讯录中联系人的详细信息
     * list为联系人详细信息的jsonobject对象字符串,此方法可根据需要改写
     * <p>
     * 读取联系人
     * <p>
     * 分为以下步骤：
     * <p>
     * 1、先读取contacts表，获取ContactsID；
     * <p>
     * 2、再在raw_contacts表中根据ContactsID获取RawContactsID；
     * <p>
     * 3、然后就可以在data表中根据RawContactsID获取该联系人的各数据了。
     *
     * @param context 上下文
     * @return 包含全部联系人信息的jsonobject对象的字符串
     */
    public static String getContactInfo(Context context) {

//        // 获得通讯录信息 ，URI是ContactsContract.Contacts.CONTENT_URI
        List<String> list = new ArrayList<>();
        //存储通讯录所有信息的jsonobject对象
        //mime类型
        String mimetype = "";
        //原来的cursorID
        int oldrid = -1;
        int contactId = -1;
        //获取通讯录中所有保存的联系人的RAW_CONTACT_ID,此id唯一是获取联系人单独信息的唯一ID,按照id进行排序
        JSONObject contactData = new JSONObject();
        JSONObject jsonObject = null;

        Cursor cursor = context.getContentResolver().query(Data.CONTENT_URI,
                null, null, null, Data.RAW_CONTACT_ID);
        try {

            int numm = 0;
            if (cursor != null) {
                while (cursor.moveToNext()) {//还有联系人信息时
                    //存储单个联系人详细信息的jsonobject对象

                    contactId = cursor.getInt(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
                    if (oldrid != contactId) {//id为-1时表示已结束
                        jsonObject = new JSONObject();
                        contactData.put("contact" + numm, jsonObject);
                        numm++;
                        oldrid = contactId;
                    }

                    // 取得联系人名字相关的mimetype类型
                    mimetype = cursor.getString(cursor.getColumnIndex(Data.MIMETYPE));
                    // 获得通讯录中每个联系人的ID
                    // 获得通讯录中联系人的名字
                    if (StructuredName.CONTENT_ITEM_TYPE.equals(mimetype)) {//联系人名字相关

                        //联系人完整名称
                        String display_name = cursor.getString(cursor.getColumnIndex(StructuredName.DISPLAY_NAME));
                        jsonObject.put("display_name", display_name);

                        //前缀?????
                        String prefix = cursor.getString(cursor.getColumnIndex(StructuredName.PREFIX));
                        jsonObject.put("prefix", prefix);

                        // 首姓名---姓,英文姓名习惯
                        String firstName = cursor.getString(cursor.getColumnIndex(StructuredName.FAMILY_NAME));
                        jsonObject.put("firstName", firstName);

                        //中间名,英文姓名习惯
                        String middleName = cursor.getString(cursor.getColumnIndex(StructuredName.MIDDLE_NAME));
                        jsonObject.put("middleName", middleName);

                        //最后的名字,英文姓名习惯
                        String lastname = cursor.getString(cursor.getColumnIndex(StructuredName.GIVEN_NAME));
                        jsonObject.put("lastname", lastname);

                        //后缀?????
                        String suffix = cursor.getString(cursor.getColumnIndex(StructuredName.SUFFIX));
                        jsonObject.put("suffix", suffix);

                        //语音名字--首名
                        String phoneticFirstName = cursor.getString(cursor.getColumnIndex(StructuredName.PHONETIC_FAMILY_NAME));
                        jsonObject.put("phoneticFirstName", phoneticFirstName);

                        //语音名字--中间名
                        String phoneticMiddleName = cursor.getString(cursor.getColumnIndex(StructuredName.PHONETIC_MIDDLE_NAME));
                        jsonObject.put("phoneticMiddleName", phoneticMiddleName);

                        //语音名字--尾名
                        String phoneticLastName = cursor.getString(cursor.getColumnIndex(StructuredName.PHONETIC_GIVEN_NAME));
                        jsonObject.put("phoneticLastName", phoneticLastName);
                    }


                    // 获取电话信息
                    if (Phone.CONTENT_ITEM_TYPE.equals(mimetype)) {

                        // 取出电话类型
                        int phoneType = cursor.getInt(cursor.getColumnIndex(Phone.TYPE));

                        // 手机
                        if (phoneType == Phone.TYPE_MOBILE) {
                            String mobile = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("mobile", mobile);
                        }

                        // 住宅电话
                        if (phoneType == Phone.TYPE_HOME) {
                            String homeNum = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("homeNum", homeNum);
                        }

                        // 单位电话
                        if (phoneType == Phone.TYPE_WORK) {
                            String jobNum = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("jobNum", jobNum);
                        }

                        // 单位传真
                        if (phoneType == Phone.TYPE_FAX_WORK) {
                            String workFax = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("workFax", workFax);
                        }

                        // 住宅传真
                        if (phoneType == Phone.TYPE_FAX_HOME) {
                            String homeFax = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("homeFax", homeFax);
                        }

                        // 寻呼机
                        if (phoneType == Phone.TYPE_PAGER) {
                            String pager = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("pager", pager);
                        }

                        // 回拨号码
                        if (phoneType == Phone.TYPE_CALLBACK) {
                            String quickNum = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("quickNum", quickNum);
                        }

                        // 公司总机
                        if (phoneType == Phone.TYPE_COMPANY_MAIN) {
                            String jobTel = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("jobTel", jobTel);
                        }

                        // 车载电话
                        if (phoneType == Phone.TYPE_CAR) {
                            String carNum = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("carNum", carNum);
                        }

                        // ISDN
                        if (phoneType == Phone.TYPE_ISDN) {
                            String isdn = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("isdn", isdn);
                        }

                        // 总机
                        if (phoneType == Phone.TYPE_MAIN) {
                            String tel = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("tel", tel);
                        }

                        // 无线装置
                        if (phoneType == Phone.TYPE_RADIO) {
                            String wirelessDev = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("wirelessDev", wirelessDev);
                        }

                        // 电报
                        if (phoneType == Phone.TYPE_TELEX) {
                            String telegram = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("telegram", telegram);
                        }

                        // TTY_TDD
                        if (phoneType == Phone.TYPE_TTY_TDD) {
                            String tty_tdd = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("tty_tdd", tty_tdd);
                        }

                        // 单位手机
                        if (phoneType == Phone.TYPE_WORK_MOBILE) {
                            String jobMobile = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("jobMobile", jobMobile);
                        }

                        // 单位寻呼机
                        if (phoneType == Phone.TYPE_WORK_PAGER) {
                            String jobPager = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("jobPager", jobPager);
                        }

                        // 助理
                        if (phoneType == Phone.TYPE_ASSISTANT) {
                            String assistantNum = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("assistantNum", assistantNum);
                        }

                        // 彩信
                        if (phoneType == Phone.TYPE_MMS) {
                            String mms = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                            jsonObject.put("mms", mms);
                        }

                    }

                    // 查找email地址
                    if (Email.CONTENT_ITEM_TYPE.equals(mimetype)) {

                        // 取出邮件类型
                        int emailType = cursor.getInt(cursor.getColumnIndex(Email.TYPE));

                        // 住宅邮件地址
                        if (emailType == Email.TYPE_CUSTOM) {
                            String homeEmail = cursor.getString(cursor.getColumnIndex(Email.DATA));
                            jsonObject.put("homeEmail", homeEmail);
                        }

                        // 住宅邮件地址
                        else if (emailType == Email.TYPE_HOME) {
                            String homeEmail = cursor.getString(cursor.getColumnIndex(Email.DATA));
                            jsonObject.put("homeEmail", homeEmail);
                        }
                        // 单位邮件地址
                        if (emailType == Email.TYPE_CUSTOM) {
                            String jobEmail = cursor.getString(cursor.getColumnIndex(Email.DATA));
                            jsonObject.put("jobEmail", jobEmail);
                        }

                        // 单位邮件地址
                        else if (emailType == Email.TYPE_WORK) {
                            String jobEmail = cursor.getString(cursor.getColumnIndex(Email.DATA));
                            jsonObject.put("jobEmail", jobEmail);
                        }
                        // 手机邮件地址
                        if (emailType == Email.TYPE_CUSTOM) {
                            String mobileEmail = cursor.getString(cursor.getColumnIndex(Email.DATA));
                            jsonObject.put("mobileEmail", mobileEmail);
                        }

                        // 手机邮件地址
                        else if (emailType == Email.TYPE_MOBILE) {
                            String mobileEmail = cursor.getString(cursor.getColumnIndex(Email.DATA));
                            jsonObject.put("mobileEmail", mobileEmail);
                        }
                    }

                    // 查找event地址
                    if (Event.CONTENT_ITEM_TYPE.equals(mimetype)) {

                        // 取出时间类型
                        int eventType = cursor.getInt(cursor.getColumnIndex(Event.TYPE));

                        // 生日
                        if (eventType == Event.TYPE_BIRTHDAY) {
                            String birthday = cursor.getString(cursor.getColumnIndex(Event.START_DATE));
                            jsonObject.put("birthday", birthday);
                        }

                        // 周年纪念日
                        if (eventType == Event.TYPE_ANNIVERSARY) {
                            String anniversary = cursor.getString(cursor.getColumnIndex(Event.START_DATE));
                            jsonObject.put("anniversary", anniversary);
                        }

                        // 备注
                        if (eventType == Event.TYPE_OTHER) {
                            String remark = cursor.getString(cursor.getColumnIndex(Event.START_DATE));
                            jsonObject.put("remark", remark);

                        }

                    }


                    // 即时消息
                    if (Im.CONTENT_ITEM_TYPE.equals(mimetype)) {
                        // 取出即时消息类型
                        int protocal = cursor.getInt(cursor.getColumnIndex(Im.PROTOCOL));


                        if (Im.TYPE_CUSTOM == protocal) {

                            String workMsg = cursor.getString(cursor.getColumnIndex(Im.DATA));
                            jsonObject.put("workMsg", workMsg);

                        } else if (Im.PROTOCOL_MSN == protocal) {

                            String workMsg = cursor.getString(cursor.getColumnIndex(Im.DATA));
                            jsonObject.put("workMsg", workMsg);

                        }
                        if (Im.PROTOCOL_QQ == protocal) {

                            String instantsMsg = cursor.getString(cursor.getColumnIndex(Im.DATA));
                            jsonObject.put("instantsMsg", instantsMsg);

                        }
                    }

                    // 获取备注信息
                    if (Note.CONTENT_ITEM_TYPE.equals(mimetype)) {
                        String remark = cursor.getString(cursor.getColumnIndex(Note.NOTE));
                        jsonObject.put("remark", remark);
                    }

                    // 获取昵称信息
                    if (Nickname.CONTENT_ITEM_TYPE.equals(mimetype)) {
                        String nickName = cursor.getString(cursor.getColumnIndex(Nickname.NAME));
                        jsonObject.put("nickName", nickName);
                    }

                    // 获取组织信息
                    if (Organization.CONTENT_ITEM_TYPE.equals(mimetype)) {

                        // 取出组织类型
                        int orgType = cursor.getInt(cursor.getColumnIndex(Organization.TYPE));

                        // 单位
                        if (orgType == Organization.TYPE_CUSTOM) {
//     if (orgType == Organization.TYPE_WORK) {
                            String company = cursor.getString(cursor.getColumnIndex(Organization.COMPANY));
                            jsonObject.put("company", company);
                            String jobTitle = cursor.getString(cursor.getColumnIndex(Organization.TITLE));
                            jsonObject.put("jobTitle", jobTitle);
                            String department = cursor.getString(cursor.getColumnIndex(Organization.DEPARTMENT));
                            jsonObject.put("department", department);
                        }
                    }


                    // 获取网站信息
                    if (Website.CONTENT_ITEM_TYPE.equals(mimetype)) {

                        // 取出组织类型
                        int webType = cursor.getInt(cursor.getColumnIndex(Website.TYPE));

                        // 主页
                        if (webType == Website.TYPE_CUSTOM) {
                            String home = cursor.getString(cursor.getColumnIndex(Website.URL));
                            jsonObject.put("home", home);
                        }

                        // 主页
                        else if (webType == Website.TYPE_HOME) {
                            String home = cursor.getString(cursor.getColumnIndex(Website.URL));
                            jsonObject.put("home", home);
                        }

                        // 个人主页
                        if (webType == Website.TYPE_HOMEPAGE) {
                            String homePage = cursor.getString(cursor.getColumnIndex(Website.URL));
                            jsonObject.put("homePage", homePage);
                        }

                        // 工作主页
                        if (webType == Website.TYPE_WORK) {
                            String workPage = cursor.getString(cursor.getColumnIndex(Website.URL));
                            jsonObject.put("workPage", workPage);
                        }

                    }

                    // 查找通讯地址
                    if (StructuredPostal.CONTENT_ITEM_TYPE.equals(mimetype)) {

                        // 取出邮件类型
                        int postalType = cursor.getInt(cursor.getColumnIndex(StructuredPostal.TYPE));

                        // 单位通讯地址
                        if (postalType == StructuredPostal.TYPE_WORK) {

                            String street = cursor.getString(cursor.getColumnIndex(StructuredPostal.STREET));
                            jsonObject.put("street", street);

                            String ciry = cursor.getString(cursor.getColumnIndex(StructuredPostal.CITY));
                            jsonObject.put("ciry", ciry);

                            String box = cursor.getString(cursor.getColumnIndex(StructuredPostal.POBOX));
                            jsonObject.put("box", box);

                            String area = cursor.getString(cursor.getColumnIndex(StructuredPostal.NEIGHBORHOOD));
                            jsonObject.put("area", area);

                            String state = cursor.getString(cursor.getColumnIndex(StructuredPostal.REGION));
                            jsonObject.put("state", state);

                            String zip = cursor.getString(cursor.getColumnIndex(StructuredPostal.POSTCODE));
                            jsonObject.put("zip", zip);

                            String country = cursor.getString(cursor.getColumnIndex(StructuredPostal.COUNTRY));
                            jsonObject.put("country", country);


                        }
                        // 住宅通讯地址
                        if (postalType == StructuredPostal.TYPE_HOME) {

                            String homeStreet = cursor.getString(cursor.getColumnIndex(StructuredPostal.STREET));
                            jsonObject.put("homeStreet", homeStreet);

                            String homeCity = cursor.getString(cursor.getColumnIndex(StructuredPostal.CITY));
                            jsonObject.put("homeCity", homeCity);

                            String homeBox = cursor.getString(cursor.getColumnIndex(StructuredPostal.POBOX));
                            jsonObject.put("homeBox", homeBox);

                            String homeArea = cursor.getString(cursor.getColumnIndex(StructuredPostal.NEIGHBORHOOD));
                            jsonObject.put("homeArea", homeArea);

                            String homeState = cursor.getString(cursor.getColumnIndex(StructuredPostal.REGION));
                            jsonObject.put("homeState", homeState);

                            String homeZip = cursor.getString(cursor.getColumnIndex(StructuredPostal.POSTCODE));
                            jsonObject.put("homeZip", homeZip);

                            String homeCountry = cursor.getString(cursor.getColumnIndex(StructuredPostal.COUNTRY));
                            jsonObject.put("homeCountry", homeCountry);

                        }
                        // 其他通讯地址
                        if (postalType == StructuredPostal.TYPE_OTHER) {

                            String otherStreet = cursor.getString(cursor.getColumnIndex(StructuredPostal.STREET));
                            jsonObject.put("otherStreet", otherStreet);

                            String otherCity = cursor.getString(cursor.getColumnIndex(StructuredPostal.CITY));
                            jsonObject.put("otherCity", otherCity);

                            String otherBox = cursor.getString(cursor.getColumnIndex(StructuredPostal.POBOX));
                            jsonObject.put("otherBox", otherBox);

                            String otherArea = cursor.getString(cursor.getColumnIndex(StructuredPostal.NEIGHBORHOOD));
                            jsonObject.put("otherArea", otherArea);

                            String otherState = cursor.getString(cursor.getColumnIndex(StructuredPostal.REGION));
                            jsonObject.put("otherState", otherState);

                            String otherZip = cursor.getString(cursor.getColumnIndex(StructuredPostal.POSTCODE));
                            jsonObject.put("otherZip", otherZip);

                            String otherCountry = cursor.getString(cursor.getColumnIndex(StructuredPostal.COUNTRY));
                            jsonObject.put("otherCountry", otherCountry);
                        }
                    }
                    if (!TextUtils.isEmpty(jsonObject.toString())) {
                        list.add(jsonObject.toString());
                        Log.e("TAG", "getContactInfo: ---单条联系人记录--" + jsonObject.toString());
                    }
                }
            }

        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Log.e("TAG", "getContactInfo: ---全部联系人记录--" + contactData.toString());
        return contactData.toString();
    }

    /**
     * 删除指定联系人
     * 核心思想：
     * (1)先在raw_contacts表根据姓名(此处的姓名为name记录的data2的数据而不是data1的数据)查出id；
     * (2)在data表中只要raw_contact_id匹配的都删除；
     */
    public static void deleteContacts(Context context, String name) {
        //根据姓名求id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Data._ID}, "display_name=?", new String[]{name}, null);
        try {
            if (cursor != null) {
                while (cursor.moveToFirst()) {
                    int id = cursor.getInt(0);
                    //根据id删除data中的相应数据
                    resolver.delete(uri, "display_name=?", new String[]{name});
                    uri = Uri.parse("content://com.android.contacts/data");
                    resolver.delete(uri, "raw_contact_id=?", new String[]{id + ""});
                }
            }
        } catch (Exception e) {
            ToastUtils.showToast(context, R.string.shanchulianxirenshibai);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


    }


    /**
     * 查询所有的通话记录
     * 实际使用时使用bean对象进行存储
     *
     * @param context 上下文
     * @return 查询记录
     */
    public static List<Map<String, String>> getCallRecord(Context context) {
        // 1.获得ContentResolver
        ContentResolver resolver = context.getContentResolver();
        // 2.利用ContentResolver的query方法查询通话记录数据库
//        /**
//         * @param uri 需要查询的URI，（这个URI是ContentProvider提供的）
//         * @param projection 需要查询的字段
//         * @param selection sql语句where之后的语句
//         * @param selectionArgs ?占位符代表的数据
//         * @param sortOrder 排序方式
//         *
//         */
        List<Map<String, String>> list = new ArrayList<>();
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
////            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 1111);
//            Log.e("TAG", "getDataList: -----请求授权");
//            ToastUtils.showToast(context, R.string.weishouyuduqutonghuajiluquanxian);
//        } else {
//            Log.e("TAG", "getDataList: -----直接运行");
        //执行时需要授予读取通话记录权限  Manifest.permission.READ_CALL_LOG
        @SuppressLint("MissingPermission") Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, // 查询通话记录的URI
                new String[]{CallLog.Calls.CACHED_NAME// 通话记录的联系人
                        , CallLog.Calls.NUMBER// 通话记录的电话号码
                        , CallLog.Calls.DATE// 通话记录的日期
                        , CallLog.Calls.DURATION// 通话时长
                        , CallLog.Calls.TYPE// 通话类型
                }//???????
                , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        try {
            // 3.通过Cursor获得数据
            if (cursor != null) {
                while (cursor.moveToNext()) {

                    String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));


                    long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault()).format(new Date(dateLong));

                    int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

                    String typeString = "";
                    switch (type) {
                        case CallLog.Calls.INCOMING_TYPE:
                            typeString = "呼入";
                            break;
                        case CallLog.Calls.OUTGOING_TYPE:
                            typeString = "呼出";
                            break;
                        case CallLog.Calls.MISSED_TYPE:
                            typeString = "未接";
                            break;
                        default:
                            break;
                    }
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", (name == null) ? "未备注联系人" : name);
                    map.put("number", number);
                    map.put("date", date);
                    map.put("duration", duration + "秒");
                    map.put("type", typeString);
                    Log.e("TAG", "getDataList: -----" + map);
                    list.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


//        }

        return list;
    }


}
