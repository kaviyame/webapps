import Component from '@glimmer/component';
import { action } from '@ember/object';
import { service } from '@ember/service';
export default class AddtocartComponent extends Component {
  @service router;

  @action
  addToCart() {
    //alert(p_name);
    // var s = property => {
    //     throw new Error(`You accessed \`this.${String(property)}\` from a function passed to the ${source}, but the function itself was not bound to a valid \`this\` context. Consider updating to use a bound function (for instance, use an arrow function, \`() => {}\`).`);
    //   };
    // var { s } = `this.${"#selectedquan"};
    // alert(s);
    // alert(`${`this.${"#selectedquan"}`}`);
    // alert(`${selectedquan}.value`);
    //alert(document.getElementById("selectedquan").value);
    var p_name = this.args.p_name;
    var selectedquan = document.getElementById(p_name).value;

    let url = 'http://localhost:8082/ShopApp/AddToCart';

    let fetchOptions = {
      //HTTP method set to POST.
      method: 'POST',
      //Set the headers that specify you're sending a JSON body request and accepting JSON response
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
      },
      credentials: 'include',
      // POST request body as JSON string.
      body: `{p_name: "${p_name}", selectedquan: "${selectedquan}"}`,
    };

    //Get the response body as JSON.
    //If the response was not OK, throw an error.

    const fetchPromise = fetch(url, fetchOptions);
    fetchPromise
      .then((response) => {
        return response.json();
      })
      .then((res) => {
        this.router.refresh();
      });
  }
}
