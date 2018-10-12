package com.company.service;

import java.util.HashMap;
import java.util.Map;

import com.company.model.CarPickupInfo;
import org.kie.server.springboot.jbpm.ContainerAliasResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicationController {

    private String containerAlias = "sample-ifttt-kjar";
    private String processId = "com.myspace.sample_ifttt_kjar.ifttt-demo";
    private String triggerName = "orderpickup";

    @Autowired
    private ContainerAliasResolver aliasResolver;

    @GetMapping("/demo")
    public String getDemo(Model model) {
        model.addAttribute("carpickupinfo", new CarPickupInfo());
        return "demo";
    }

    @PostMapping("/demo")
    public String postDemo(@ModelAttribute CarPickupInfo carPickupInfo, Model model) {
        model.addAttribute("carpickupinfo", carPickupInfo);
        model.addAttribute("processid", processId);
        model.addAttribute("containerid", aliasResolver.latest(containerAlias));


        Map<String, Object> processInputs = new HashMap<>();
        processInputs.put("fname",
                          carPickupInfo.getFirstName());
        processInputs.put("lname",
                          carPickupInfo.getLastName());
        processInputs.put("address",
                          carPickupInfo.getAddress());
        processInputs.put("triggername",
                          triggerName);
        model.addAttribute("processinputs", processInputs);

        return "demoresults";
    }
}
