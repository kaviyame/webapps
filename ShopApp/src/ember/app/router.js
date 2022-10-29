import EmberRouter from '@ember/routing/router';
import config from 'emberapp/config/environment';

export default class Router extends EmberRouter {
  location = config.locationType;
  rootURL = config.rootURL;
}

Router.map(function () {
  this.route('shopowner');
  this.route('customer');
  this.route('viewproducts');
  this.route('orders');
  this.route('viewmycart');
  this.route('viewmyorders');
  this.route('logout');
  this.route('errorpage');
  this.route('myproducts');
});
