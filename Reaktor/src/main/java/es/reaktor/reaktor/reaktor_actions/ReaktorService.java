package es.reaktor.reaktor.reaktor_actions;

import es.reaktor.models.*;
import es.reaktor.models.DTO.SimpleComputerDTO;
import es.reaktor.reaktor.repository.ICpuRepository;
import es.reaktor.reaktor.repository.IMotherboardMalwareRepository;
import es.reaktor.reaktor.repository.IMotherboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReaktorService
{
    @Autowired
    private IMotherboardMalwareRepository iMotherboardMalwareRepository;

    @Autowired
    private IMotherboardRepository iMotherboardRepository;

    @Autowired
    private ICpuRepository iCpuRepository;


    public List<SimpleComputerDTO> getSimpleComputerDTO()
    {
        List<SimpleComputerDTO> simpleComputerDTOS = new ArrayList<>();

        List<Motherboard> motherboardList = this.iMotherboardRepository.findAll();

        for (Motherboard motherboard : motherboardList)
        {
            Long malwareCount = this.iMotherboardMalwareRepository.countByMotherboardMalwareId_SerialNumber(motherboard.getSerialNumber());

            simpleComputerDTOS.add(new SimpleComputerDTO(motherboard.getSerialNumber(), malwareCount, motherboard.getClassroom(), motherboard.getProfessor(), motherboard.getComputerOn()));
        }
        return simpleComputerDTOS;
    }

    public Reaktor getInformationReaktor(String idComputer)
    {
        Cpu cpu = this.iCpuRepository.findCpuById_Motherboard_SerialNumber(idComputer);

        List<Malware> malwareList = this.iMotherboardMalwareRepository.findAll()
                .stream().filter(motherboardMalware -> motherboardMalware.getSerialNumber().getSerialNumber() == idComputer).toList()
                .stream().map(MotherboardMalware::getName).toList();

        return new Reaktor();
    }
}
