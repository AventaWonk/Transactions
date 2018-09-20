export const ShopSelectSettings = {
    ajax: {
        url: "/shop/search",
        dataType: 'json',
        delay: 250,
        data: params => {
            return {
                query: params.term,
            };
        },
        processResults: (data, params) => {
            return {
                results: data
            };
        },
        cache: true
    },
    theme: 'bootstrap4',
    width: '100%',
    placeholder: 'Select a shop...',
    minimumInputLength: 1,
    escapeMarkup: markup => {
        return markup;
    },
    templateResult: shop => {
        if (shop.loading) {
            return "Loading...";
        }

        return "<span class='select-product-name'>"+ shop.address +"<span class='select-product-id'> Id:"+ shop.id +"</span></span>";
    },
    templateSelection: shop => {
        if (!shop.address) {
            return shop.text;
        }

        return shop.address;
    }
};

export const ProductSelectSettings = {
    ajax: {
        url: "/product/search",
        dataType: 'json',
        delay: 250,
        data: params => {
            return {
                query: params.term,
            };
        },
        processResults: (data, params) => {
            return {
                results: data
            };
        },
        cache: true
    },
    theme: 'bootstrap4',
    width: '100%',
    placeholder: 'Select a product...',
    minimumInputLength: 1,
    escapeMarkup: markup => {
        return markup;
    },
    templateResult: product => {
        if (product.loading) {
            return "Loading...";
        }

        return "<span class='select-product-name'>"+ product.name +"<span class='select-product-id'> Id:"+ product.id +"</span></span>";
    },
    templateSelection: product => {
        if (!product.name) {
            return product.text;
        }

        return product.name;
    }
};