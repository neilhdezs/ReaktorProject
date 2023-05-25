export interface SimpleComputerDTO
{
  id: string;
  malwareCount: number;
  location: string;
  responsable: string;
  computerOn: boolean;
}

export interface ComputerDTO
{
  motherboardDTO: Motherboard;
  malwareDTO: Malware[];
  cpuDTO: Cpu;
  graphicCardDTO: GraphicCard[];
  hardDiskDTO: HardDisk[];
  internetConnectionDTO: InternetConnection;
  networkCardDTO: NetworkCard[];
  partitionDTO: Partition[];
  ramDTO: Ram[];
  soundCardDTO: SoundCard[];
}


export interface Motherboard {
  serialNumber: string;
  model: string;
  classroom: string;
  description: string;
  professor: string;
  lastConnection: Date;
  lastUpdateComputerOn: Date;
  computerOn: boolean;
}

export interface Malware {
  name: string;
  description: string;
}

export interface Cpu {
  cores: number;
  frequency: number;
  threads: number;
}

export interface GraphicCard {
  model: string;
}

export interface HardDisk {
  size: number;
  model: string;
}

export interface InternetConnection {
  networkName: string;
}

export interface NetworkCard {
  macAddress: string;
  rj45IsConnected: string;
  model: string;
  isWireless: boolean;
}

export interface Partition {
  size: number;
  letter: string;
  operatingSystem: string;
}

export interface Ram {
  size: number;
  occupiedSlots: string;
  model: string;
  type: string;
  speed: number;
}

export interface SoundCard {
  model: string;
  driver: string;
}

