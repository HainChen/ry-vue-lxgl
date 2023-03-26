import request from '@/utils/request'

// 查询教务管理列表
export function listJwc(query) {
  return request({
    url: '/lxgl/jwc/list',
    method: 'get',
    params: query
  })
}

// 查询教务管理详细
export function getJwc(id) {
  return request({
    url: '/lxgl/jwc/' + id,
    method: 'get'
  })
}

// 新增教务管理
export function addJwc(data) {
  return request({
    url: '/lxgl/jwc',
    method: 'post',
    data: data
  })
}

// 修改教务管理
export function updateJwc(data) {
  return request({
    url: '/lxgl/jwc',
    method: 'put',
    data: data
  })
}

// 删除教务管理
export function delJwc(id) {
  return request({
    url: '/lxgl/jwc/' + id,
    method: 'delete'
  })
}
