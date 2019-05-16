package njgis.opengms.portal.service;

import com.alibaba.fastjson.JSONObject;
import njgis.opengms.portal.dao.TaskDao;
import njgis.opengms.portal.dao.UserDao;
import njgis.opengms.portal.dto.UserUpdateDTO;
import njgis.opengms.portal.entity.ModelItem;
import njgis.opengms.portal.entity.Task;
import njgis.opengms.portal.entity.User;
import njgis.opengms.portal.dto.UserAddDTO;
import njgis.opengms.portal.enums.ResultEnum;
import njgis.opengms.portal.exception.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    TaskDao taskDao;

    @Autowired
    CommonService commonService;


    public String resetPassword(String email){
        try {
            Random random = new Random();
            String password = "";
            for (int i = 0; i < 10; i++) {
                int num = random.nextInt(62);
                if (num >= 0 && num < 10) {
                    password += num;
                } else if (num >= 10 && num < 36) {
                    num -= 10;
                    num += 65;
                    char c = (char) num;
                    password += c;
                } else {
                    num -= 36;
                    num += 97;
                    char c = (char) num;
                    password += c;
                }
            }

            User user=userDao.findFirstByEmail(email);
            if(user!=null){
                user.setPassword(password);
                userDao.save(user);
                String subject="OpenGMS Portal Password Reset";
                String content="Your password has been reset to <b>"+password+"</b>.";

                commonService.sendEmail(email,subject,content);

                return "suc";
            }
            else {
                return "no user";
            }

        }
        catch (Exception e){
            return "fail";
        }
    }
    //++
    public void modelItemPlusPlus(String userName){
        User user = userDao.findFirstByUserName(userName);
        int count=user.getModelItems();
        user.setModelItems(++count);
        userDao.save(user);
    }
    public void dataItemPlusPlus(String userName){
        User user = userDao.findFirstByUserName(userName);
        int count=user.getDataItems();
        user.setDataItems(++count);
        userDao.save(user);
    }
    public void conceptualModelPlusPlus(String userName){
        User user = userDao.findFirstByUserName(userName);
        int count=user.getConceptualModels();
        user.setConceptualModels(++count);
        userDao.save(user);
    }
    public void logicalModelPlusPlus(String userName){
        User user = userDao.findFirstByUserName(userName);
        int count=user.getLogicalModels();
        user.setLogicalModels(++count);
        userDao.save(user);
    }
    public void computableModelPlusPlus(String userName){
        User user = userDao.findFirstByUserName(userName);
        int count=user.getComputableModels();
        user.setComputableModels(++count);
        userDao.save(user);
    }
    //--
    public void modelItemMinusMinus(String userName){
        User user = userDao.findFirstByUserName(userName);
        int count=user.getModelItems();
        user.setModelItems(--count);
        userDao.save(user);
    }
    public void dataItemMinusMinus(String userName){
        User user = userDao.findFirstByUserName(userName);
        int count=user.getDataItems();
        user.setDataItems(--count);
        userDao.save(user);
    }
    public void conceptualModelMinusMinus(String userName){
        User user = userDao.findFirstByUserName(userName);
        int count=user.getConceptualModels();
        user.setConceptualModels(--count);
        userDao.save(user);
    }
    public void logicalModelMinusMinus(String userName){
        User user = userDao.findFirstByUserName(userName);
        int count=user.getLogicalModels();
        user.setLogicalModels(--count);
        userDao.save(user);
    }
    public void computableModelMinusMinus(String userName){
        User user = userDao.findFirstByUserName(userName);
        int count=user.getComputableModels();
        user.setComputableModels(--count);
        userDao.save(user);
    }

    public User getByUid(String userName){
        try {
            return userDao.findFirstByUserName(userName);
        } catch (Exception e) {
            System.out.println("有人乱查数据库！！该ID不存在User对象");
            throw new MyException(ResultEnum.NO_OBJECT);
        }
    }

    public User getByOid(String id) {
        try {
            return userDao.findFirstByOid(id);
        } catch (Exception e) {
            System.out.println("有人乱查数据库！！该ID不存在User对象");
            throw new MyException(ResultEnum.NO_OBJECT);
        }
    }

    public int addUser(UserAddDTO user) {

        User u=userDao.findFirstByUserName(user.getUserName());
        if(u!=null){
            return -1;
        }
        u=userDao.findFirstByEmail(user.getEmail());
        if(u!=null){
            return -2;
        }
        else {
            User user1 = new User();
            BeanUtils.copyProperties(user, user1);
            user1.setOid(String.valueOf(userDao.findAll().size() + 1));
            user1.setCreateTime(new Date());
            List<String> orgs=new ArrayList<>();
            orgs.add(user.getOrg());
            user1.setOrganizations(orgs);
            user1.setImage("");
            user1.setDescription("");
            user1.setPhone("");
            user1.setWiki("");
            userDao.insert(user1);
            return 1;
        }
    }

    public int changePass(String oid,String oldPass,String newPass){

        User user = userDao.findFirstByOid(oid);
        String old=user.getPassword();
        if(old.equals(oldPass)){
            user.setPassword(newPass);
            userDao.save(user);
            return 1;
        }
        else{
            return -2;
        }

    }

    public int updateUser(UserUpdateDTO userUpdateDTO){

        User user=userDao.findFirstByOid(userUpdateDTO.getOid());
        BeanUtils.copyProperties(userUpdateDTO,user);
        userDao.save(user);

        return 1;
    }

    public JSONObject validPassword(String account, String password) {

        User user = userDao.findFirstByEmail(account);
        if (user == null) {
            user = userDao.findFirstByUserName(account);
        }
        if (user != null) {
            if (user.getPassword().equals(password)) {
                JSONObject result=new JSONObject();
                result.put("name",user.getName());
                result.put("oid",user.getOid());
                result.put("uid",user.getUserName());
                return result;
            }
        }
        return null;
    }

    public String getImage(String oid){
        return userDao.findFirstByOid(oid).getImage();
    }

    public JSONObject getUserInfo(String userId){
        JSONObject result=new JSONObject();
        int success=taskDao.findAllByUserIdAndStatus(userId,2).size();
        int calculating=taskDao.findAllByUserIdAndStatus(userId,0).size();
        calculating+=taskDao.findAllByUserIdAndStatus(userId,1).size();
        int failed=taskDao.findAllByUserIdAndStatus(userId,-1).size();

        JSONObject taskStatistic=new JSONObject();
        taskStatistic.put("success",success);
        taskStatistic.put("fail",failed);
        taskStatistic.put("calculating",calculating);
        result.put("record",taskStatistic);

        User user = userDao.findFirstByUserName(userId);
        result.put("userInfo",user);
        return result;
    }

    public User getUser(String userName){
        return userDao.findFirstByUserName(userName);
    }


}