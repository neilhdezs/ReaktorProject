package es.reaktor.models.DTO;

import es.reaktor.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaktorDTO extends Reaktor
{

    private MotherboardDTO motherboardDTO;

    private CpuDTO cpuDTO;



}
