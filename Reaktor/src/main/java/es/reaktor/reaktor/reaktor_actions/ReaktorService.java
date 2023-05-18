package es.reaktor.reaktor.reaktor_actions;

import es.reaktor.models.*;
import es.reaktor.models.DTO.SimpleComputerDTO;
import es.reaktor.reaktor.repository.*;
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

    @Autowired
    private IGraphicCardRepository iGraphicCardRepository;

    @Autowired
    private IHardDiskRepository iHardDiskRepository;

    @Autowired
    private INetworkCardRepository iNetworkCardRepository;

    @Autowired
    private IRamRepository iRamRepository;

    @Autowired
    private ISoundCardRepository iSoundCardRepository;

    @Autowired
    private IPartitionRepository iPartitionRepository;









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

        Motherboard motherboard = this.iMotherboardRepository.findBySerialNumber(idComputer);

        List<GraphicCard> graphicCardList = this.iGraphicCardRepository.findAll()
                .stream().filter(graphicCard -> graphicCard.getId().getMotherboard().getSerialNumber() == idComputer).toList();

        List<HardDisk> hardDiskList = this.iHardDiskRepository.findAll()
                .stream().filter(hardDisk -> hardDisk.getId().getMotherboard().getSerialNumber() == idComputer).toList();

        List<NetworkCard> networkCardList = this.iNetworkCardRepository.findAll()
                .stream().filter(networkCard -> networkCard.getId().getMotherboard().getSerialNumber() == idComputer).toList();

        List<Ram> ramList = this.iRamRepository.findAll()
                .stream().filter(ram -> ram.getId().getMotherboard().getSerialNumber() == idComputer).toList();

        List<SoundCard> soundCardList = this.iSoundCardRepository.findAll()
                .stream().filter(soundCard -> soundCard.getId().getMotherboard().getSerialNumber() == idComputer).toList();

        List<Partition> partitionList = this.iPartitionRepository.findAll()
                .stream().filter(partition -> partition.getId().getHardDisk().getId().getMotherboard().getSerialNumber() == idComputer).toList();

        List<Malware> malwareList = this.iMotherboardMalwareRepository.findAll()
                .stream().filter(motherboardMalware -> motherboardMalware.getSerialNumber().getSerialNumber() == idComputer).toList()
                .stream().map(MotherboardMalware::getName).toList();

        return new Reaktor(motherboard,malwareList,cpu,graphicCardList,hardDiskList,networkCardList,partitionList,ramList,soundCardList);
    }
}
