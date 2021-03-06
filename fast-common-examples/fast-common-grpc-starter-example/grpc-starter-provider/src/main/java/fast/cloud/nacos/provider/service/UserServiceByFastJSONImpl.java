package fast.cloud.nacos.provider.service;


import com.alibaba.fastjson.JSONObject;
import fast.cloud.nacos.grpc.api.entity.UserEntity;
import fast.cloud.nacos.grpc.api.service.UserServiceByFastJSON;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class UserServiceByFastJSONImpl implements UserServiceByFastJSON {

    /**
     * 模拟数据库存储用户信息
     */
    private Map<Long, UserEntity> userMap = new ConcurrentHashMap<>();

    @Override
    public void insert(String userEntityJson) {
        UserEntity userEntity = JSONObject.parseObject(userEntityJson, UserEntity.class);
        if (userEntity == null){
            log.warn("insert user fail, userEntity is null!");
            return ;
        }
        userMap.putIfAbsent(userEntity.getId(), userEntity);
    }

    @Override
    public void deleteById(String id) {
        if (StringUtils.isEmpty(id)){
            log.warn("delete user fail, id is null!");
        }
        userMap.remove(Long.valueOf(id));
    }

    @Override
    public List<UserEntity> findAll() {
        Collection<UserEntity> values = userMap.values();
        return new ArrayList<>(values);
    }

}
