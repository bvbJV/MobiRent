package cat.copernic.appvehicles.controller;

import cat.copernic.appvehicles.dto.ClientProfileDTO;
import cat.copernic.appvehicles.dto.ClientUpdateDTO;
import cat.copernic.backendProjecte3.business.ClientService;
import cat.copernic.backendProjecte3.entities.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{dni}")
    public ResponseEntity<ClientProfileDTO> getClient(@PathVariable String dni) {

        Client client = clientService.obtenirPerDni(dni);

        ClientProfileDTO dto = new ClientProfileDTO();
        dto.setDni(client.getDni());
        dto.setNomComplet(client.getNomComplet());
        dto.setEmail(client.getEmail());
        dto.setTelefon(client.getTelefon());
        dto.setAdreca(client.getAdreca());
        dto.setNacionalitat(client.getNacionalitat());
        dto.setNumeroTargetaCredit(client.getNumeroTargetaCredit());
        dto.setDataCaducitatDni(client.getDataCaducitatDni());
        dto.setTipusCarnetConduir(client.getTipusCarnetConduir());
        dto.setDataCaducitatCarnet(client.getDataCaducitatCarnet());

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{dni}")
    public ResponseEntity<ClientProfileDTO> updateClient(
            @PathVariable String dni,
            @RequestBody ClientUpdateDTO updateDTO) {

        Client updated = clientService.actualitzarPerfilPerDni(dni, updateDTO);

        ClientProfileDTO dto = new ClientProfileDTO();
        dto.setDni(updated.getDni());
        dto.setNomComplet(updated.getNomComplet());
        dto.setEmail(updated.getEmail());
        dto.setTelefon(updated.getTelefon());
        dto.setAdreca(updated.getAdreca());
        dto.setNacionalitat(updated.getNacionalitat());
        dto.setNumeroTargetaCredit(updated.getNumeroTargetaCredit());
        dto.setDataCaducitatDni(updated.getDataCaducitatDni());
        dto.setTipusCarnetConduir(updated.getTipusCarnetConduir());
        dto.setDataCaducitatCarnet(updated.getDataCaducitatCarnet());

        return ResponseEntity.ok(dto);
    }
}