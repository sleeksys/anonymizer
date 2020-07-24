import {Cell} from './Cell';
import {PrivacyLevel} from '../../enums/PrivacyLevel';

export interface Label {
  id:number;
  cellId:number;
  text:string;
  privacyLevel:PrivacyLevel;
  cells:Cell [];

}
