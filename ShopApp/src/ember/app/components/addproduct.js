import Component from '@glimmer/component';
import { action } from '@ember/object';
import { service } from '@ember/service';

export default class AddproductComponent extends Component {
  @service router;

  @action
  addProduct() {
    var p_name = document.getElementById("pnid").value;
    var price = document.getElementById("prid").value;
    var avlquan = document.getElementById("quanid").value;

    let url = 'http://localhost:8082/ShopApp/AddProduct';

    let fetchOptions = {
      //HTTP method set to POST.
      method: 'POST',
      //Set the headers that specify you're sending a JSON body request and accepting JSON response
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
      },
      credentials: 'include',
      // POST request body as JSON string.
      body: `{p_name: "${p_name}", avlquan: "${avlquan}", price: "${price}"}`,
    };

    //Get the response body as JSON.
    //If the response was not OK, throw an error.

    const fetchPromise = fetch(url, fetchOptions);
    fetchPromise
      .then((response) => {
        return response.json();
      })
      .then((res) => {
        alert(res.msg);
        this.router.refresh();
      });
  }
}
