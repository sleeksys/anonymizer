import {Label} from './Label';

export interface Cell {
  id:number;
  tokenId:number;
  rowIndex:number;
  columnIndex:number;
  text:string;
  label:Label;

}
