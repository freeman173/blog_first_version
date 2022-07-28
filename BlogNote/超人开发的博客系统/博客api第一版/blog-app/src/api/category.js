import request from '@/request'

export function getAllCategorys() {
  return request({
    url: '/categories',
    method: 'get',
  })
}

export function getAllCategorysDetail() {
  return request({
    url: '/categories/detail',
    method: 'get',
  })
}

export function getCategory(id) {
  return request({
    url: `/categories/${id}`,
    method: 'get',
  })
}

export function getCategoryDetail(id) {
  return request({
    url: `/categories/detail/${id}`,
    method: 'get',
  })
}
