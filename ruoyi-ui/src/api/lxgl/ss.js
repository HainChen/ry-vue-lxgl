import request from '@/utils/request'

// 查询家具赔偿信息列表
export function listSs(query) {
  return request({
    url: '/lxgl/ss/list',
    method: 'get',
    params: query
  })
}

// 查询家具赔偿信息详细
export function getSs(id) {
  return request({
    url: '/lxgl/ss/' + id,
    method: 'get'
  })
}

// 新增家具赔偿信息
export function addSs(data) {
  return request({
    url: '/lxgl/ss',
    method: 'post',
    data: data
  })
}

// 修改家具赔偿信息
export function updateSs(data) {
  return request({
    url: '/lxgl/ss',
    method: 'put',
    data: data
  })
}

// 删除家具赔偿信息
export function delSs(id) {
  return request({
    url: '/lxgl/ss/' + id,
    method: 'delete'
  })
}
