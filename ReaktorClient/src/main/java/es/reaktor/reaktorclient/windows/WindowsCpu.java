package es.reaktor.reaktorclient.windows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import es.reaktor.models.Cpu;
import es.reaktor.models.Motherboard;
import es.reaktor.models.Id.CpuId;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;

@Service
public class WindowsCpu
{

    /**
     * - Logger -
     * This logger is used to log the information of the application
     */
    private final Logger LOGGER = LogManager.getLogger();


    /**
     * - Attribute -
     * this class is used to get the hardware information of the computer
     */
    private final WindowsHardwareAbstractionLayer windowsHardwareAbstractionLayer;


    public WindowsCpu()
    {
        this.windowsHardwareAbstractionLayer = new WindowsHardwareAbstractionLayer();
    }

    public Cpu getCpu(Motherboard motherboard)
    {
        Cpu cpu = new Cpu();
        cpu.setCores(this.getCores());
        cpu.setFrequency(this.getFrequency());
        cpu.setThreads(this.getThreads());
        cpu.setId(this.getCpuId(motherboard));
        LOGGER.info("CPU INFO OBTAINED");
        return cpu;
    }

    private CpuId getCpuId(Motherboard motherboard)
    {
        CpuId cpuId = new CpuId(this.windowsHardwareAbstractionLayer.getProcessor().getProcessorIdentifier().getProcessorID(), motherboard);
        return cpuId;
    }

    private Integer getThreads()
    {
        return this.windowsHardwareAbstractionLayer.getProcessor().getLogicalProcessorCount();
    }

    private long getFrequency()
    {

        return this.windowsHardwareAbstractionLayer.getProcessor().getProcessorIdentifier().getVendorFreq()/1000/1000;
    }

    private Integer getCores()
    {
        return this.windowsHardwareAbstractionLayer.getProcessor().getPhysicalProcessorCount();
    }

}
