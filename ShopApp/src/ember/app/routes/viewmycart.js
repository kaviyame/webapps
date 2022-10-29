import Route from '@ember/routing/route';

export default class ViewmycartRoute extends Route {
  async model() {
    let response1 = await fetch(`http://localhost:8082/ShopApp/ViewCart`, {
      credentials: 'include',
      //'Access-Control-Allow-Credentials':true,
      //'Access-Control-Allow-Headers':'Access-Control-Allow-Credentials, credentials'
    });
    let data1 = await response1.json();
    return data1.cart;
  }
}
