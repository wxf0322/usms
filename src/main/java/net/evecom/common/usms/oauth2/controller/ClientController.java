/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.controller;

import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.oauth2.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 描述 ClientController
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:39
 */
@Controller
@RequestMapping("/client")
public class ClientController {

    /**
     * 注入ApplicationService
     */
    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("clientList", applicationService.findAll());
        return "client/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        model.addAttribute("client", new ApplicationEntity());
        model.addAttribute("op", "新增");
        return "client/edit";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ApplicationEntity application, RedirectAttributes redirectAttributes) {
        application.setEnabled(1L);
        applicationService.createApplication(application);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/client";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", applicationService.findOne(id));
        model.addAttribute("op", "修改");
        return "client/edit";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(ApplicationEntity application, RedirectAttributes redirectAttributes) {
        application.setEnabled(1L);
        applicationService.updateApplication(application);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/client";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", applicationService.findOne(id));
        model.addAttribute("op", "删除");
        return "client/edit";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        applicationService.deleteApplication(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/client";
    }

}
