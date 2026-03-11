/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.backendProjecte3.business;

import cat.copernic.backendProjecte3.entities.Client;
import cat.copernic.backendProjecte3.entities.Reserva;
import cat.copernic.backendProjecte3.entities.Vehicle;
import cat.copernic.backendProjecte3.exceptions.ReservaNoTrobadaException;
import cat.copernic.backendProjecte3.repository.ClientRepository;
import cat.copernic.backendProjecte3.repository.ReservaRepository;
import cat.copernic.backendProjecte3.repository.VehicleRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            VehicleRepository vehicleRepository,
            ClientRepository clientRepository
    ) {
        this.reservaRepository = reservaRepository;
        this.vehicleRepository = vehicleRepository;
        this.clientRepository = clientRepository;
    }

    public Reserva crearReserva(String matricula, String emailClient, LocalDate inici, LocalDate fi) {

        // validar fechas
        if (inici.isAfter(fi)) {
            throw new RuntimeException("La data d'inici no pot ser posterior a la data fi");
        }

        // buscar vehicle
        Vehicle vehicle = vehicleRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Vehicle no trobat"));

        // buscar client
        Client client = clientRepository.findById(emailClient)
                .orElseThrow(() -> new RuntimeException("Client no trobat"));

        // calcular dias
        long dies = ChronoUnit.DAYS.between(inici, fi);
        if (dies == 0) {
            dies = 1;
        }

        // validar minimo dias
        if (dies < vehicle.getMinDiesLloguer()) {
            throw new RuntimeException(
                    "Aquest vehicle requereix un mínim de "
                    + vehicle.getMinDiesLloguer() + " dies de lloguer"
            );
        }

        // validar maximo dias
        if (dies > vehicle.getMaxDiesLloguer()) {
            throw new RuntimeException(
                    "Aquest vehicle permet un màxim de "
                    + vehicle.getMaxDiesLloguer() + " dies de lloguer"
            );
        }

        // calcular precio total
        BigDecimal preuDia = vehicle.getPreuHora();
        BigDecimal total = preuDia.multiply(BigDecimal.valueOf(dies));

        // crear reserva
        Reserva reserva = new Reserva();
        reserva.setVehicle(vehicle);
        reserva.setClient(client);
        reserva.setDataInici(inici);
        reserva.setDataFi(fi);
        reserva.setImportTotal(total);
        reserva.setFiancaPagada(vehicle.getFiancaEstandard());

        // guardar
        return reservaRepository.save(reserva);
    }

    // ===============================
    // METODOS QUE USA EL CONTROLLER
    // ===============================

    public List<Reserva> obtenirPerClient(String email) {
        return reservaRepository.findByClient_Email(email);
    }

    public Reserva obtenirPerId(Long id) throws ReservaNoTrobadaException {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoTrobadaException("Reserva no trobada"));
    }

    public void anularReserva(Long id, String userName) throws ReservaNoTrobadaException {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoTrobadaException("Reserva no trobada"));

        reservaRepository.delete(reserva);
    }
}