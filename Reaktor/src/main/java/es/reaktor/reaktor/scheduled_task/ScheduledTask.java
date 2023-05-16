package es.reaktor.reaktor.scheduled_task;

import es.reaktor.models.Motherboard;
import es.reaktor.reaktor.repository.IMotherboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * - Class -
 * This class is used to schedule the tasks of the application
 */
@Component
public class ScheduledTask
{

    @Autowired
    private IMotherboardRepository iMotherboardRepository;

    @Scheduled(fixedRate = 20_000)
    public void checkComputerOn()
    {
        List<Motherboard> motherboards = this.iMotherboardRepository.findAll();

        for (Motherboard motherboard : motherboards)
        {
            if (motherboard.getComputerOn())
            {
                if (System.currentTimeMillis() - motherboard.getLastUpdateComputerOn().getTime() > 30_000)
                {
                    motherboard.setComputerOn(false);
                    this.iMotherboardRepository.save(motherboard);
                }
            }
        }
    }
}
