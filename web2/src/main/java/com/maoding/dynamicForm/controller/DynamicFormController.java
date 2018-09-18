package com.maoding.dynamicForm.controller;

import com.maoding.core.base.controller.BaseController;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.dynamicForm.dto.FormDTO;
import com.maoding.dynamicForm.dto.FormQueryDTO;
import com.maoding.dynamicForm.dto.SaveDynamicAuditDTO;
import com.maoding.dynamicForm.dto.SaveDynamicFormDTO;
import com.maoding.dynamicForm.service.DynamicFormFieldValueService;
import com.maoding.dynamicForm.service.DynamicFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 作者：FYT
 * 日期：2018/9/13
 */
@Controller
@RequestMapping("iWork/dynamicForm")
public class DynamicFormController extends BaseController {

    @Autowired
    DynamicFormService dynamicFormService;
    @Autowired
    DynamicFormFieldValueService dynamicFormFieldValueService;


    @ModelAttribute
    public void before() {
        this.currentUserId = this.getFromSession("userId", String.class);
        this.currentCompanyId = this.getFromSession("companyId", String.class);
        this.currentCompanyUserId = this.getFromSession("companyUserId", String.class);
    }

    /**
     * 作者：FYT
     * 日期：2018/9/13
     * 描述：保存审核表单模板
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertDynamicForm", method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage insertDynamicForm(@RequestBody SaveDynamicFormDTO dto) throws Exception{
        updateCurrentUserInfo(dto);
        return AjaxMessage.succeed(dynamicFormService.insertDynamicForm(dto));
    }

    /**
     * 作者：FYT
     * 日期：2018/9/13
     * 描述：保存审核表单内容
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveAuditDetail", method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage saveAuditDetail(@RequestBody SaveDynamicAuditDTO dto) throws Exception{
        updateCurrentUserInfo(dto);
        return AjaxMessage.succeed(dynamicFormFieldValueService.saveAuditDetail(dto));
    }

    /**
     * 作者：FYT
     * 日期：2018/9/14
     * 描述：审批表 启用/停用
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/startOrStopDynamicForm", method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage startOrStopDynamicForm(@RequestBody SaveDynamicFormDTO dto) throws Exception{
        updateCurrentUserInfo(dto);
        return AjaxMessage.succeed(dynamicFormService.startOrStopDynamicForm(dto));
    }

    /**
     * 作者：FYT
     * 日期：2018/9/14
     * 描述：审批表 删除
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteDynamicForm", method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage deleteDynamicForm (@RequestBody SaveDynamicFormDTO dto) throws  Exception{
        updateCurrentUserInfo(dto);
        return AjaxMessage.succeed(dynamicFormService.deleteDynamicForm(dto));
    }


    /**
     * 描述     查询动态表单应显示的控件及相应属性
     * 日期     2018/9/13
     * @author  张成亮
     * @return  动态表单内各控件的位置、名称等信息
     * @param   query
     **/
    @RequestMapping(value = "/getFormDetail", method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage getFormDetail(@RequestBody FormQueryDTO query) throws Exception {
        updateCurrentUserInfo(query);
        FormDTO detail = dynamicFormService.getFormDetail(query);
        return AjaxMessage.succeed(detail);
    }

    /**
     * 作者：FYT
     * 日期：2018/9/17
     * 描述：后台管理-审批管理-操作，seq排序对调
     * 接口：iWork/dynamicForm/setDynamicFormSeq
     * 参数：SaveDynamicFormDTO   ID,seq
     */
    @RequestMapping(value = "/setDynamicFormSeq", method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage setDynamicFormSeq(@RequestBody SaveDynamicFormDTO dto,SaveDynamicFormDTO dto2) throws Exception {

        return AjaxMessage.succeed(dynamicFormService.setDynamicFormSeq(dto,dto2));
    }

}
