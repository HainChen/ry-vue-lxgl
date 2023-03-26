import request from '@/utils/request'

// 查询财务处管理列表
export function listCwc(query) {
  return request({
    url: '/lxgl/cwc/list',
    method: 'get',
    params: query
  })
}

// 查询财务处管理详细
export function getCwc(id) {
  return request({
    url: '/lxgl/cwc/' + id,
    method: 'get'
  })
}

// 新增财务处管理
export function addCwc(data) {
  return request({
    url: '/lxgl/cwc',
    method: 'post',
    data: data
  })
}

// 修改财务处管理
export function updateCwc(data) {
  return request({
    url: '/lxgl/cwc',
    method: 'put',
    data: data
  })
}

// 删除财务处管理
export function delCwc(id) {
  return request({
    url: '/lxgl/cwc/' + id,
    method: 'delete'
  })
}
