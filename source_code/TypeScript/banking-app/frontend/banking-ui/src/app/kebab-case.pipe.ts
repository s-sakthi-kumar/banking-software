import {Pipe, PipeTransform} from '@angular/core';

function toKebabCase(value: string): string {
  return value.toLowerCase().replace(/ /g, '-');
}

@Pipe({name: 'kebabCase'})
export class KebabCasePipe implements PipeTransform {
  transform(value: string): string {
    return toKebabCase(value);

  }
}
