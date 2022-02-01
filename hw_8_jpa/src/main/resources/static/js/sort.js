function sort(sort) {
    let params = new URLSearchParams(window.location.search)
    let page = params.get("page")
    let size = params.get("size")
    let order = params.get("order")

    if (page !== null && size !== null) {
        params.set('page', page)
        params.set('size', size)
    }
    if (order === null || "ASC" === order.toUpperCase()) {
        order = "DESC"
    } else {
        order = "ASC"
    }
    params.set('sort', sort)
    params.set('order', order)

    window.location.href = location.protocol + '//' + location.host + location.pathname + "?" + params.toString()
}