import Route from '@ember/routing/route';
import { service } from '@ember/service';

export default class LogoutRoute extends Route {
  @service router;
  async model() {
    let response1 = await fetch(`http://localhost:8082/ShopApp/Logout`, {
      credentials: 'include',
      //'Access-Control-Allow-Credentials':true,
      //'Access-Control-Allow-Headers':'Access-Control-Allow-Credentials, credentials'
    });
    let data1 = await response1.json();
    if (data1.userType === 'Customer') {
      this.router.transitionTo('customer');
    } else {
      this.router.transitionTo('shopowner');
    }
    return data1;
  }
}
