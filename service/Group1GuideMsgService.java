/**  
 * All rights Reserved, Designed By www.maihaoche.com
 * 
 * @Package com.mhc.guide.core.biz.service
 * @author: 三帝（sandi@maihaoche.com）
 * @date: 2018-11-30 09:37:59
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved. 
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */ 
package com.mhc.guide.core.biz.service;

import com.mhc.framework.common.base.biz.BaseService;
import com.mhc.guide.dal.domain.Group1GuideMsg;
import com.mhc.guide.dal.domain.Group1GuideTemplate;

import java.util.List;

/**   
 * <p>  业务接口 </p>
 *   
 * @author: 三帝（sandi@maihaoche.com）
 * @date: 2018-11-30 09:37:59
 * @since V1.0 
 */
public interface Group1GuideMsgService extends BaseService {


    /**
     * <p> 获取全部Group1GuideMsg </p>
     *
     * @MethodName selectAll
     * @return List<Group1GuideMsg>
     * @author shimiao
     */
    List<Group1GuideMsg> selectAll();

    List<Group1GuideTemplate> selectAllTemplate(String templateRange);

    String send();
}
