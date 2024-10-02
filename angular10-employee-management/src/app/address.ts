export class Address {
    [x: string]: any;

    id: number=0;
    state: string='';
    city: string = '';
    street: string = '';
    pinCode: string = '';
    country: string = '';
    fullAddress: string = '';    
    active: boolean= false;
}