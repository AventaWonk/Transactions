import queryString from 'query-string';
import {ProductSelectSettings, ShopSelectSettings} from './Constants/Select2Settings';
import {DatePickerSettings} from './Constants/DatePickerSettings';
import {SelectorSettings} from './Constants/SelectorSettings';
import {selector} from './Components/Selector';

//Init Select2s
$('#inputShop').select2(ShopSelectSettings);
$('#inputProduct').select2(ProductSelectSettings);

//Init DatePicker
$('#inputStart').datepicker(DatePickerSettings);
$('#inputEnd').datepicker(DatePickerSettings);

selector(SelectorSettings);


// selectorConfig.forEach(target => {
//     $(target.el).on( "click", (e) => {
//         let parsedParams = queryString.parse(location.search);
//         let selectedDataset = e.target.selectedOptions[0].dataset;
//         if (selectedDataset.isDefault) {
//             target.dataParams.forEach(param => {
//                 delete parsedParams[param];
//             })
//         } else {
//             target.dataParams.forEach(param => {
//                 parsedParams[param] = selectedDataset[param];
//             })
//         }

//         window.location.search = queryString.stringify(parsedParams);
//     });
// })

// let parsedParams = queryString.parse(location.search);
// selectorConfig.forEach(target => {
//     let isSelected = target.dataParams.every(param => parsedParams[param])
//     if (isSelected) {
//         let selectElement = $(target.el)[0];
//         let selectElementOptions = selectElement.options;
//         for (let i = 0; i < selectElementOptions.length; i++) {
//             let optionData = selectElementOptions[i].dataset;
//             if (target.dataParams.every(param => optionData[param] == parsedParams[param])) {
//                 selectElement.selectedIndex = i;
//                 break;
//             }
//         }
//     }
// })
