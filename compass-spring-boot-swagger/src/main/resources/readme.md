# swagger配置示例
# 注意版本号分割在配置文件中不要用点号，例如v1.0.0 解析会变成v1 并且相应的对象也为空对象
swagger:
  read-from-db: true
  enabled: true
  api-info:
    "v1-0-0":
      {title: 'xx接口文档',description: 'xx接口文档',termsOfServiceUrl: '',contactName: 'wanmk',contactUrl: 'https://gitee.com/milkove',contactEmail: '524623302@qq.com',license: '',licenseUrl: ''}
  api-group:
    "v1-0-0":
      - {name: 'so',tags: ['so'],prefixPath: '/api/so',baseServerUrl : 'http://localhost:9301',description : '智能工单模块',hidden : false,needAuth : true}
  api-list:
    "so":
      - {tags:['so'],scanClientPath:['cn.com.bgy.iot.saas.smartorder.client.order'],scanModelPath:['cn.com.bgy.iot.saas.smartorder.vo.request','cn.com.bgy.iot.saas.smartorder.vo.response'],sortNum: 1}
