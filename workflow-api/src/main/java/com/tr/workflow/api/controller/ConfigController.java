package com.tr.workflow.api.controller;

import com.tr.dal.mybatis.mapper.CustomMapper;
import com.tr.dal.mybatis.model.*;
import com.tr.dal.mybatis.uo.ConfigUO;
import org.activiti.engine.ManagementService;
import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;
import org.activiti.engine.impl.cmd.CustomSqlExecution;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping(value = "/config")
public class ConfigController {
	@Resource
	private ManagementService managementService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ConfigUO all() throws Exception {
		ConfigUO configUO = new ConfigUO();

		CustomSqlExecution<CustomMapper, List<ListFormPO>> customSqlExecution1 =
		new AbstractCustomSqlExecution<CustomMapper, List<ListFormPO>>(CustomMapper.class) {
			public List<ListFormPO> execute(CustomMapper customMapper) {
				return customMapper.listFormAll();
			}
		};
		List<ListFormPO> listFormAll = managementService.executeCustomSql(customSqlExecution1);
		configUO.setListFormAll(listFormAll);

		CustomSqlExecution<CustomMapper, List<ListFieldPO>> customSqlExecution2 =
				new AbstractCustomSqlExecution<CustomMapper, List<ListFieldPO>>(CustomMapper.class) {
					public List<ListFieldPO> execute(CustomMapper customMapper) {
						return customMapper.listFieldAll();
					}
				};
		List<ListFieldPO> listFieldAll = managementService.executeCustomSql(customSqlExecution2);
		configUO.setListFieldAll(listFieldAll);

		CustomSqlExecution<CustomMapper, List<FormStatusPO>> customSqlExecution3 =
				new AbstractCustomSqlExecution<CustomMapper, List<FormStatusPO>>(CustomMapper.class) {
					public List<FormStatusPO> execute(CustomMapper customMapper) {
						return customMapper.formStatusAll();
					}
				};
		List<FormStatusPO> formStatusAll = managementService.executeCustomSql(customSqlExecution3);
		configUO.setFormStatusAll(formStatusAll);

		CustomSqlExecution<CustomMapper, List<FormFieldPO>> customSqlExecution4 =
				new AbstractCustomSqlExecution<CustomMapper, List<FormFieldPO>>(CustomMapper.class) {
					public List<FormFieldPO> execute(CustomMapper customMapper) {
						return customMapper.formFieldAll();
					}
				};
		List<FormFieldPO> formFieldAll = managementService.executeCustomSql(customSqlExecution4);
		configUO.setFormFieldAll(formFieldAll);


		CustomSqlExecution<CustomMapper, List<FormListPO>> customSqlExecution5 =
				new AbstractCustomSqlExecution<CustomMapper, List<FormListPO>>(CustomMapper.class) {
					public List<FormListPO> execute(CustomMapper customMapper) {
						return customMapper.formListAll();
					}
				};
		List<FormListPO> formListAll = managementService.executeCustomSql(customSqlExecution5);
		configUO.setFormListAll(formListAll);

		CustomSqlExecution<CustomMapper, List<FieldInfoPO>> customSqlExecution6 =
				new AbstractCustomSqlExecution<CustomMapper, List<FieldInfoPO>>(CustomMapper.class) {
					public List<FieldInfoPO> execute(CustomMapper customMapper) {
						return customMapper.fieldInfoAll();
					}
				};
		List<FieldInfoPO> fieldInfoAll = managementService.executeCustomSql(customSqlExecution6);
		configUO.setFieldInfoAll(fieldInfoAll);


		CustomSqlExecution<CustomMapper, List<FormDisplayGroupPO>> customSqlExecution7 =
				new AbstractCustomSqlExecution<CustomMapper, List<FormDisplayGroupPO>>(CustomMapper.class) {
					public List<FormDisplayGroupPO> execute(CustomMapper customMapper) {
						return customMapper.formDisplayGroupAll();
					}
				};
		List<FormDisplayGroupPO> formDisplayGroupAll = managementService.executeCustomSql(customSqlExecution7);
		configUO.setFormDisplayGroupAll(formDisplayGroupAll);
		return configUO;
	}

}