import request from '@/utils/request'


//查询各环节办理情况
export function getLxhjHandleStatus() {
  return request({
    url: '/lxgl/hj/getLxhjHandleStatus',
    method: 'get'
  })
}

//查询学院毕业生数量
export function getcollegePersonNum() {
  return request({
    url: '/lxgl/hj/getcollegePersonNum',
    method: 'get'
  })
}

//查询学院毕业生离校情况数量
export function getcollegeLxss() {
  return request({
    url: '/lxgl/hj/getcollegeLxss',
    method: 'get'
  })
}

// 查询离校资格列表
export function listHj(query) {
  return request({
    url: '/lxgl/hj/list',
    method: 'get',
    params: query
  })
}
//更新离校申请最新数据
export function updateLxhjMess() {
  return request({
    url: '/lxgl/hj/updateLxhjMess',
    method: 'put',
  })
}

// 查询离校资格详细
export function getHj(id) {
  return request({
    url: '/lxgl/hj/' + id,
    method: 'get'
  })
}

// 查询自己的离校资格详细
export function getLxhj() {
  return request({
    url: '/lxgl/hj/stu' ,
    method: 'get'
  })
}

// 查询自己的离校资格详细
export function updateLxhj() {
  return request({
    url: '/lxgl/hj/updateList' ,
    method: 'get'
  })
}


// 新增离校资格
export function addHj(data) {
  return request({
    url: '/lxgl/hj',
    method: 'post',
    data: data
  })
}

// 修改离校资格
export function updateHj(data) {
  return request({
    url: '/lxgl/hj',
    method: 'put',
    data: data
  })
}

// 删除离校资格
export function delHj(id) {
  return request({
    url: '/lxgl/hj/' + id,
    method: 'delete'
  })
}
