package es.reaktor.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Reaktor {

    protected Motherboard motherboard;

    protected List<Malware> malware;

    protected Cpu cpu;

    protected List<GraphicCard> graphicCard;

    protected List<HardDisk> hardDisk;

    private InternetConnection internetConnection;

    protected List<NetworkCard> networkCard;

    protected List<Partition> partition;

    protected List<Ram> ram;

    protected List<SoundCard> soundCard;

}
