package com.tr.dal.mybatis.mapper;

import com.tr.dal.mybatis.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yangjie on 2018/2/5.
 */
public interface CustomMapper {
    @Insert("INSERT INTO approval_record (order_id, operate_name, operation, task_id, task_name, process_instance_id, comment, external_response) VALUES (#{arg0}, #{arg1}, #{arg2}, #{arg3}, #{arg4}, #{arg5}, #{arg6}, #{arg7})")
    Boolean approvalRecordAdd(String orderId, String operateName, String operation, String taskId, String taskName, String processInstanceId, String comment, String externalResponse);


    @Select("SELECT * FROM approval_record where order_id=#{orderId} order by create_time asc")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "orderId",  column = "order_id"),
            @Result(property = "operateName",  column = "operate_name"),
            @Result(property = "operation", column = "operation"),
            @Result(property = "taskId", column = "task_id"),
            @Result(property = "taskName", column = "task_name"),
            @Result(property = "processInstanceId", column = "process_instance_id"),
            @Result(property = "comment",  column = "comment"),
            @Result(property = "createTime",  column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    List<ApprovalRecordPO> approvalRecordQuery(String orderId);

    @Delete("DELETE FROM approval_record WHERE order_id=#{orderId}")
    Integer approvalRecordDelete(String orderId);

    @Select("select l.list_key, f.form_key from list_field_group l, form_info f where l.list_key = f.list_key")
    @Results({
            @Result(property = "listKey",  column = "list_key"),
            @Result(property = "formKey",  column = "form_key")
    })
    List<ListFormPO> listFormAll();


    @Select("select l.list_key, f.field_name, f.field_desc, f.field_type, f.dict_type_code, f.dict_filter, f.domain from list_field_group l, field_group_field gf, field_info f   where l.field_group_name = gf.field_group_name and gf.field_name = f.field_name order by gf.id")
    @Results({
            @Result(property = "listKey",  column = "list_key"),
            @Result(property = "fieldName",  column = "field_name"),
            @Result(property = "fieldDesc",  column = "field_desc"),
            @Result(property = "fieldType",  column = "field_type"),
            @Result(property = "dictTypeCode",  column = "dict_type_code"),
            @Result(property = "dictFilter",  column = "dict_filter"),
            @Result(property = "domain",  column = "domain")

    })
    List<ListFieldPO> listFieldAll();


    @Select("select process_key,form_key,operation_order_status from process_form")
    @Results({
            @Result(property = "processKey",  column = "process_key"),
            @Result(property = "formKey",  column = "form_key"),
            @Result(property = "operationOrderStatus",  column = "operation_order_status")
    })
    List<FormStatusPO> formStatusAll();


    @Select("select f.form_key, fgf.field_name, fg.display_type, fi.field_desc, fi.field_type, fi.dict_type_code, fi.dict_filter, fi.domain from form_info f, form_field_group fg, field_group_field fgf, field_info fi where f.form_key = fg.form_key and fg.field_group_name = fgf.field_group_name and fgf.field_name=fi.field_name order by f.form_key")
    @Results({
            @Result(property = "formKey",  column = "form_key"),
            @Result(property = "fieldName",  column = "field_name"),
            @Result(property = "displayType",  column = "display_type"),
            @Result(property = "fieldDesc",  column = "field_desc"),
            @Result(property = "fieldType",  column = "field_type"),
            @Result(property = "dictTypeCode",  column = "dict_type_code"),
            @Result(property = "dictFilter",  column = "dict_filter"),
            @Result(property = "domain",  column = "domain")

    })
    List<FormFieldPO> formFieldAll();


    @Select("select * from form_list")
    @Results({
            @Result(property = "formKey",  column = "form_key"),
            @Result(property = "formListKey",  column = "form_list_key"),
            @Result(property = "displayType",  column = "display_type")

    })
    List<FormListPO> formListAll();


    @Select("select * from field_info")
    @Results({
            @Result(property = "fieldName",  column = "field_name"),
            @Result(property = "fieldDesc",  column = "field_desc"),
            @Result(property = "fieldType",  column = "field_type"),
            @Result(property = "dictTypeCode",  column = "dict_type_code"),
            @Result(property = "dictFilter",  column = "dict_filter"),
            @Result(property = "domain",  column = "domain")
    })
    List<FieldInfoPO> fieldInfoAll();

    @Select("select * from form_display_group")
    @Results({
            @Result(property = "formKey",  column = "form_key"),
            @Result(property = "displayGroupName",  column = "display_group_name"),
            @Result(property = "displayType",  column = "display_type")
    })
    List<FormDisplayGroupPO> formDisplayGroupAll();
}
