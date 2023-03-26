import request from '@/utils/request'

// 查询图书管理列表
export function listTsg(query) {
  return request({
    url: '/lxgl/tsg/list',
    method: 'get',
    params: query
  })
}

// 查询图书管理详细
export function getTsg(id) {
  return request({
    url: '/lxgl/tsg/' + id,
    method: 'get'
  })
}

// 新增图书管理
export function addTsg(data) {
  return request({
    url: '/lxgl/tsg',
    method: 'post',
    data: data
  })
}

// 修改图书管理
export function updateTsg(data) {
  return request({
    url: '/lxgl/tsg',
    method: 'put',
    data: data
  })
}

// 删除图书管理
export function delTsg(id) {
  return request({
    url: '/lxgl/tsg/' + id,
    method: 'delete'
  })
}
