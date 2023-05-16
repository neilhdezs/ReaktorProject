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
  motherbard: Motherboard;
  malware: Malware[];
  cpu: Cpu;
  graphicCard: GraphicCard[];
  hardDisk: HardDisk[];
  internetConnection: InternetConnection;
  networkCard: NetworkCard[];
  partition: Partition[];
  ram: Ram[];
  soundCard: SoundCard[];
}


interface Motherboard {
  serialNumber: string;
  model: string;
  classroom: string;
  description: string;
  professor: string;
  lastConnection: Date;
  lastUpdateComputerOn: Date;
  computerOn: boolean;
}

interface Malware {
  name: string;
  description: string;
}

interface Cpu {
  cores: number;
  frequency: number;
  threads: number;
}

interface GraphicCard {
  model: string;
}

interface HardDisk {
  size: number;
  model: string;
}

interface InternetConnection {
  networkName: string;
}

interface NetworkCard {
  macAddress: string;
  rj45IsConnected: string;
  model: string;
  isWireless: boolean;
}

interface Partition {
  size: number;
  letter: string;
  operatingSystem: string;
}

interface Ram {
  size: number;
  occupiedSlot: string;
  model: string;
  type: string;
  speed: number;
}

interface SoundCard {
  model: string;
  driver: string;
}

