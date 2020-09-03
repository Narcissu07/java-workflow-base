package com.tr.workflow.api.freecontroller;

import java.io.InputStream;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
public class HistoryProcessController {
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessEngineConfiguration processEngineConfiguration;
    @Resource
    private HistoryService historyService;
    /**
     * 读取带跟踪的图片
     */
    @GetMapping("/historic-process-instances/{processInstanceId}/diagram")
    public ResponseEntity<byte[]> historyDiagram(HttpServletResponse response, @PathVariable String processInstanceId) throws Exception {
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        ProcessDefinition pde = this.repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
        if ((pde != null) && (pde.hasGraphicalNotation())) {
            BpmnModel bpmnModel = this.repositoryService.getBpmnModel(pde.getId());
            ProcessDiagramGenerator diagramGenerator = this.processEngineConfiguration.getProcessDiagramGenerator();
            List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
            List<String> nodes = new ArrayList<>();
            List<String> flows = new ArrayList<>();
            Set<String> his_nodes = new HashSet<>();
            Set<String> his_flows = new HashSet<>();
            for(int i=0; i<historicActivityInstanceList.size(); i++){
                HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(i);
                if(!his_nodes.contains(historicActivityInstance.getActivityId())){
                    his_nodes.add(historicActivityInstance.getActivityId());
                }
                if(i==0){
                    getHighLines(bpmnModel,null,historicActivityInstance.getActivityId(),his_flows);
                }else{
                    HistoricActivityInstance fromIns = historicActivityInstanceList.get(i-1);
                    getHighLines(bpmnModel,fromIns.getActivityId(),historicActivityInstance.getActivityId(),his_flows);
                }

            }
            nodes.addAll(his_nodes);
            flows.addAll(his_flows);
            InputStream resource = diagramGenerator.generateDiagram(bpmnModel, "png", nodes, flows, this.processEngineConfiguration
                    .getActivityFontName(), this.processEngineConfiguration.getLabelFontName(), this.processEngineConfiguration
                    .getAnnotationFontName(), this.processEngineConfiguration.getClassLoader(), 1.0D);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "image/png");
            try{
                return new ResponseEntity(IOUtils.toByteArray(resource), responseHeaders, HttpStatus.OK);
            }catch (Exception e){
                throw new ActivitiIllegalArgumentException("Error exporting diagram", e);
            }
        }
        throw new ActivitiIllegalArgumentException("Process instance with id '" + processInstance.getId() + "' has no graphical notation defined.");
    }

    /**
     * 获取高亮的线
     * @param bpmnModel
     * @param
     * @return
     */
    private Set<String> getHighLines(BpmnModel bpmnModel, String formKey,String toKey,Set<String> highLines){
        FlowNode toNode=(FlowNode) bpmnModel.getFlowElement(toKey);
        List<SequenceFlow> pvmTransitions = toNode.getIncomingFlows();
        if(formKey==null){
            for(SequenceFlow sf:pvmTransitions) {
                if (highLines.contains(sf.getId())) {
                    continue;
                }
                highLines.add(sf.getId());
            }
        }else{
            for(SequenceFlow sf:pvmTransitions){
                if(highLines.contains(sf.getId())){
                    continue;
                }
                if(formKey.equals(sf.getSourceRef())){
                    highLines.add(sf.getId());
                }
            }
        }
        return highLines;
    }
}
