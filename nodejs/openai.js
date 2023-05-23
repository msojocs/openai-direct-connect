
const https = require('https')

const openAi = () => {
    return new Promise((resolve, reject) => {
        let data = ''
        
        const req = https.request({
            port: 443,
            host: '52.152.96.252',
            method: 'GET',
            path: 'https://api.openai.com/v1/models',
            rejectUnauthorized: false,
            timeout: 30,
            headers: {
                Authorization: `Bearer token`
            }
        });

        req.end();
        req.on('response', (res) => {
            res.on('data', chunk => {
                data += chunk
            })
            res.on('end', () => {
                // console.log('end:', data)
                const ret = JSON.parse(data)
                if (ret.error)
                    reject(ret)
                else
                    resolve(ret)
            })
            res.on('error', err => {
                // console.log('err1:', data)
                reject(err)
            })
        });
        req.on('error', err => {
            // console.log('err2:', data)
            reject(err)
        })

    })
}

(async () => {
    try {
            
        const result = await openAi()
        console.log(result)
    } catch (error) {
        console.log('error:', error)
    }
})()