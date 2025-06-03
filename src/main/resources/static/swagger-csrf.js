fetch("/csrf-token", { credentials: 'include' })
  .then(res => res.json())
  .then(token => {
    const csrf = token.token || token.headerValue || token._csrf;

    window.ui.getConfigs().requestInterceptor = req => {
      req.headers['X-XSRF-TOKEN'] = csrf;
      return req;
    };
    console.log("✅ Swagger CSRF token attached");
  })
  .catch(err => console.error("❌ Swagger CSRF fetch failed", err));
