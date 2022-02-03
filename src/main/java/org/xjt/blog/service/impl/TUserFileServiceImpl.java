package org.xjt.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.xjt.blog.entity.TUserFile;
import org.xjt.blog.mapper.TUserFileMapper;
import org.xjt.blog.service.TUserFileService;
import org.xjt.blog.utils.RespBean;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Service
public class TUserFileServiceImpl implements TUserFileService {
    @Autowired
    private TUserFileMapper tUserFileMapper;

    @Override
    public Integer uploadFile(TUserFile userFile) {
        userFile.setUploadTime(DateUtil.now());
        int insert = tUserFileMapper.insert(userFile);
        return insert;
    }

    @Override
    public RespBean getAllFilesByUserId(Long id) {
        List<TUserFile> fileList = tUserFileMapper.selectList(new QueryWrapper<TUserFile>().eq("user_id",id));
        return RespBean.ok("成功获取用户所有文件",fileList);
    }

    @Override
    public TUserFile getByUserFileId(Long id) {
        TUserFile userFile = tUserFileMapper.selectById(id);

        return userFile;
    }

    @Override
    public void updateUserFile(TUserFile userFile) {
        tUserFileMapper.updateById(userFile);
    }

    @Override
    public void deleteUserFile(Long id) {
        int i = tUserFileMapper.deleteById(id);
    }

    @Override
    public RespBean getCountsGroupByUserAndType() {
        List<Map<String, Object>> mapList = tUserFileMapper.getCountsGroupByUserAndFileType();
        if(ObjectUtils.isEmpty(mapList)){
            return RespBean.warn("error");
        }else{
            return RespBean.ok("ok",mapList);
        }
    }

    @Override
    public RespBean queryFileByCondition(Integer current, Integer pageSize, String type, String user_id) {
        Page<TUserFile> page = new Page<>(current,pageSize);
        QueryWrapper<TUserFile> wrapper = new QueryWrapper<>();

        if(!ObjectUtils.isEmpty(user_id) && StringUtils.hasText(user_id)){
            wrapper.eq("user_id",user_id);
        }
        if(!ObjectUtils.isEmpty(type) && StringUtils.hasText(type) && !"0".equalsIgnoreCase(type)){
            System.out.println("type===================>"+type);
            wrapper.eq("file_type",type);
        }

        tUserFileMapper.selectPage(page,wrapper);
        List<TUserFile> records = page.getRecords();
        if(ObjectUtils.isEmpty(records)){
            return RespBean.warn("empty");
        }else{
            return RespBean.ok("ok",page);
        }
    }

    @Override
    public RespBean backGetAllNum() {
        Integer count = tUserFileMapper.selectCount(null);
        if(count<0){
            return RespBean.warn("empty");
        }else{
            return RespBean.ok("ok",count);
        }
    }
}
