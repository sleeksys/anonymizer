import {Label} from './Label';

export interface Token {
  id:number;
  sessionId:string;
  value:string;
  dateStart:Date;
  dateEnd:Date;

  label:Label;

}
