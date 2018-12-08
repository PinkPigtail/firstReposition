/**  
  * All rights Reserved, Designed By www.maihaoche.com
 *
  * @Package com.mhc.guide.core.biz.service.impl
  * @author: 三帝（sandi@maihaoche.com）
  * @date: 2018-11-30 09:37:59
  * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
  * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
  */
package com.mhc.guide.core.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mhc.bs.common.base.page.PageInfo;
import com.mhc.bs.common.exception.BizException;
import com.mhc.framework.common.base.biz.BaseServiceImpl;
import com.mhc.guide.api.vo.Group1GuideTemplatePageVo;
import com.mhc.guide.core.biz.convert.Group1GuideTemplateMapstruct;
import com.mhc.guide.core.biz.convert.PageMapStruct;
import com.mhc.guide.core.biz.enums.GuideErrorCodeEnum;
import com.mhc.guide.core.biz.service.Group1GuideTemplateService;
import com.mhc.guide.dal.domain.Group1GuideTemplate;
import com.mhc.guide.dal.manager.Group1GuideTemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**  
  * <p>  服务实现类 </p>
 *  
  * @author: 三帝（sandi@maihaoche.com）
  * @date: 2018-11-30 09:37:59
 * @since V1.0
 */
@Service
public class Group1GuideTemplateServiceImpl extends BaseServiceImpl implements Group1GuideTemplateService {
    @Autowired
    Group1GuideTemplateManager group1GuideTemplateManager;

    //获取所有的模板
    public List<Group1GuideTemplate> getAllTemplate(){
        Wrapper<Group1GuideTemplate> wrapper = new EntityWrapper<>();
        List<Group1GuideTemplate> templateList = group1GuideTemplateManager.selectList(wrapper);

        return templateList;
    }

    //获取特定发送范围的模板
    public List<Group1GuideTemplate> getTemplateByRange(String range) {
        //校验参数
        if(StringUtils.isEmpty(range)){
            throw new BizException(String.valueOf(GuideErrorCodeEnum.PARAM_NULL.getCode()), GuideErrorCodeEnum.PARAM_NULL.getDesc());
        }
        EntityWrapper entityWrapper = new EntityWrapper(new Group1GuideTemplate());
        entityWrapper.like("templateRange", range);
        //通过发送范围查询模板
        List<Group1GuideTemplate> tempList = group1GuideTemplateManager.selectList(entityWrapper);
        return tempList;
    }

    /**
     * <p> 模板分页查询 </p>
     *
     * @param 
     * @return null
     * @throws 
     * @author 文远（wenyuan@maihaoche.com）
     * @since V1.1.0-SNAPSHOT
     */
    @Override
    public PageInfo<Group1GuideTemplate> selectGuideTempPage(Group1GuideTemplatePageVo pageVo) {
        Page<Group1GuideTemplate> page =
                group1GuideTemplateManager.selectPage(Group1GuideTemplateMapstruct.INSTANCE.guideTempPageVo2PlusPage(pageVo));

        return PageMapStruct.INSTACE.plusPage2MhcPageInfo(page);
    }

    //新增模板
    public boolean addTemplate(Group1GuideTemplate group1GuideTemplate){
        //校验参数(不需要校验模板ID —— ID自增)
        if(StringUtils.isEmpty(group1GuideTemplate.getTemplateName())||
            StringUtils.isEmpty(group1GuideTemplate.getTemplateTitle())||
                StringUtils.isEmpty(group1GuideTemplate.getTemplateContent())||
                StringUtils.isEmpty(group1GuideTemplate.getTemplateRange()))
        {
            return false;
            //throw new BizException(String.valueOf(GuideErrorCodeEnum.PARAM_NULL.getCode()), GuideErrorCodeEnum.PARAM_NULL.getDesc());
        }
        //将模板加入数据库
        int count = group1GuideTemplateManager.insert(group1GuideTemplate);
        return count>0 ? true:false;
    }

    //更新模板
    public boolean updateTemplate(Group1GuideTemplate group1GuideTemplate){
        //校验参数
        if(StringUtils.isEmpty(group1GuideTemplate.getTemplateName())||
                StringUtils.isEmpty(group1GuideTemplate.getTemplateTitle())||
                StringUtils.isEmpty(group1GuideTemplate.getTemplateContent())||
                StringUtils.isEmpty(group1GuideTemplate.getTemplateRange()))
        {
            return false;
            //throw new BizException(String.valueOf(GuideErrorCodeEnum.PARAM_NULL.getCode()), GuideErrorCodeEnum.PARAM_NULL.getDesc());
        }
        //将该模板更新至数据库
        int count = group1GuideTemplateManager.updateById(group1GuideTemplate);
        return count>0 ? true:false;
    }

    //删除一个模板
    public boolean deleteTemplateById(Integer id){
        //校验参数
        if(id == null){
            return false;
        }
        //删除该模板
        int count = group1GuideTemplateManager.deleteById(id);
        return count>0?true:false;
    }

    //获取所有的模板发送范围(每个范围用英文|隔开）
    public String selectAllRange(){
        Wrapper<Group1GuideTemplate> wrapper = new EntityWrapper<>();
        wrapper.isNotNull("template_range");
        List<Group1GuideTemplate> templateList= group1GuideTemplateManager.selectList(wrapper);
        StringBuilder strings = new StringBuilder();
        //如果一条数据也没有返回""
        if(CollectionUtils.isEmpty(templateList)){
            return "";
        }
        for (Group1GuideTemplate template:templateList) {
            strings.append(template.getTemplateRange());
            strings.append("|");
        }
        strings.deleteCharAt(strings.length()-1);
        return strings.toString();
    }

}
