import Component from '@glimmer/component';
import { service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class LoginComponent extends Component {
  customer = this.args.customer === 'true' ? true : false;
  @service router;
  postFormFieldsAsJson = async ({ url, formData }) => {
    //Create an object from the form data entries
    let formDataObject = Object.fromEntries(formData.entries());
    // Format the plain form data as JSON

    let formDataJsonString = JSON.stringify(formDataObject);

    //Set the fetch options (headers, body)
    let fetchOptions = {
      //HTTP method set to POST.
      method: 'POST',
      //Set the headers that specify you're sending a JSON body request and accepting JSON response
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
      },

      // POST request body as JSON string.
      body: formDataJsonString,
    };

    //Get the response body as JSON.
    //If the response was not OK, throw an error.

    const fetchPromise = fetch(url, fetchOptions);
    fetchPromise
      .then((response) => {
        return response.json();
      })
      .then((res) => {
        let result = res.result == 'true' ? true : false;
        alert('result is ' + result);
        if (result) {
          let customer = this.args.customer === 'true' ? true : false;
          if (customer) {
            this.router.transitionTo('viewproducts');
          } else {
            this.router.transitionTo('orders');
          }

          console.log('Logged In');
        } else {
          alert(res.result);
          //this.router.transitionTo('errorpage',queryParams);
        }
      });

    // await fetch(url, fetchOptions).then(response => {
    //     return response.json();
    //   }).then(result => {
    //     console.log();
    //   });

    //console.log(res.json());
    //If the response is not ok throw an error (for debugging)
    // if (!res.ok) {
    //   let error = await res.text();
    //   throw new Error(error);
    // }

    //If the response was OK, return the response body.
    //return response;
  };

  submit = async (e) => {
    //Add an event listener to the form element and handler for the submit an event.

    /**
     * Prevent the default browser behaviour of submitting
     * the form so that you can handle this instead.
     */
    e.preventDefault();

    /**
     * Get the element attached to the event handler.
     */
    let form = e.currentTarget;

    /**
     * Take the URL from the form's `action` attribute.
     */
    let url = form.action;

    try {
      /**
       * Takes all the form fields and make the field values
       * available through a `FormData` instance.
       */
      let formData = new FormData(form);

      /**
       * The `postFormFieldsAsJson()` function in the next step.
       */

      // let responseData = { postFormFieldsAsJson({ url, formData }){

      // }};
      this.postFormFieldsAsJson({ url, formData });

      //Destructure the response data
      //let serverDataResponse = responseData.json();

      //Display the response data in the console (for debugging)
      //console.log(serverDataResponse);

      // if (serverDataResponse.result){
      //     console.log("here");
      // };
    } catch (error) {
      //If an error occurs display it in the console (for debugging)
      console.error(error);
    }
  };
}
