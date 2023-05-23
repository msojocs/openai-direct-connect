from urllib import request, error
import ssl
import json

headers = {
    'Authorization': 'Bearer token',
    'HOST': 'api.openai.com'
}
url = 'https://52.152.96.252/v1/models'
try:
    opener = request.build_opener()

    opener.addheaders = [headers]
    context = ssl._create_unverified_context()
    req = request.Request(url, headers=headers)
    r = request.urlopen(req, context=context)
    print(r)
except error.HTTPError as e:
    data = e.read()
    # print(data)
    print(json.loads(data))
    