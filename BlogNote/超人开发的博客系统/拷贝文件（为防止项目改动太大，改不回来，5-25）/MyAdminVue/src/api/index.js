import request from '../utils/request';


//查询用户人员
export const fetchData = query => {
    return request({
        url: '/data/sysUsers',
        method: 'get',
        params: query
    });
};

export const editData = query => {
    return request({
        url: '/data/editSysUser',
        method: 'post',
        data: query
    });
};

export const deleteData = query => {
    return request({
        url: `/data/deleteSysUser/${query}`,
        method: 'get',
        // params: query
    });
};

// /文章相关api
export const fetchArticleData = query => {
    return request({
        url: '/data/articles',
        method: 'get',
        params: query
    });
};

export const deleteArticleData = query => {
    return request({
        url: `/data/deleteArticle/${query}`,
        method: 'get',
        // params: query
    });
};


//绘图相关api
export const getPieData = query => {
    return request({
        url: `/data/getPieData`,
        method: 'get',
        // params: query
    });
};
export const getLineData = query => {
    return request({
        url: `/data/getLineData`,
        method: 'get',
        // params: query
    });
};

export const getBarData = query => {
    return request({
        url: `/data/getBarData`,
        method: 'get',
        // params: query
    });
};
