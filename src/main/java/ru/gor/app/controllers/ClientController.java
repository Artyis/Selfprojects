package ru.gor.app.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gor.app.models.dto.client.ClientDto;
import ru.gor.app.service.ClientService;

import javax.validation.Valid;

@AllArgsConstructor
@Builder
@Controller
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clientList", clientService.getAllClientsDTO());
        model.addAttribute("newClient", new ClientDto());
        return "index";
    }

    @PostMapping()
    public String addNew(@ModelAttribute("newClient") @Valid ClientDto client,
                         BindingResult bindingResult,
                         Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("clientList", clientService.getAllClientsDTO());
            return "index";
        }
        clientService.saveClient(client);
        return "redirect:/client";
    }

    @GetMapping("/{id}")
    public String editAndDelete(@PathVariable("id") int id, Model model) {
        model.addAttribute("clientDto", clientService.getClientById(id));
        model.addAttribute("id", id);
        return "edit";
    }

    @PatchMapping("/{id}")
    public String editClient(@PathVariable("id") int id,
                             @ModelAttribute("clientDto")
                             @Valid ClientDto client,
                             BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "edit";
        }
        clientService.editClient(client, id);
        return "redirect:/client";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        clientService.deleteClient(id);
        return "redirect:/client";
    }

}
