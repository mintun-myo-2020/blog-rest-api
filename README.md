
# Blog Rest API

Rest API for a personal blogging project


## API Endpoints and Sample Request/Response Bodies

### GET
#### BLOG POSTS
`ALL logged in users` api/posts  

`ALL logged in users` api/posts/**{id}**  
#### COMMENTS
`ALL logged in users` api/posts/**{postId}**/comments  
`ALL logged in users` api/posts/**{postId}**/comments/**{commentId}**  
___
### POST
#### BLOG POSTS
`logged in with ADMIN role` api/posts    
**Request:**
```json
{
    "title": "sample title",
    "description": "sample description",
    "content": "sample content"
}
```


#### COMMENTS
`ALL logged in users` api/posts/**{postId}**/comments  
**Request**
```json
{
    "name": "sample title",
    "email": "sample description",
    "body": "sample content"
}
```
#### AUTH
`To Login` /api/auth/login  
**Request:** 
```json
{
    "usernameOrEmail": "email@email.com",
    "password": "password"
}
```
**Response:** 
```json
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2QyQGdtYWlsLmNvbSIsImlhdCI6MTY3MTQzNTg4OSwiZXhwIjoxNjcyMDQwNjg5fQ.JlHlDicec3kWzdUQe7D6wxzdA4nabriRs2nbpMI35idx2LOF7t8agPv0YILMkHXfGnNL1Fbzzpqd5JOoGRUrdw",
    "tokenType": "Bearer"
}
```

`To Signup` /api/auth/signup  
**Request:** 
```json
{
    "name" : "name",
    "username": "username",
    "email": "email@email.com",
    "password": "password"
}
```
**Response**
```json
{
	"Registration Successful"
}
```
___
### PUT
#### BLOG POSTS
`logged in with ADMIN role` api/posts/**{id}**
**Request:**
```json
{
    "title": "updated title",
    "description": "updated description",
    "content": "updated content"
}
```
#### COMMENTS
`ALL logged in users` api/posts/**{postId}**/comments/**{commentId}**    
```json
{
    "name": "updated title",
    "email": "updated description",
    "body": "updated content"
}
```
___
### DELETE
`logged in with ADMIN role` api/posts/**{id}**  
___

## Contributing

[ME](https://github.com/mintun-myo-2020)


## Acknowledgments
  - **Billie Thompson** - *Provided README Template* -
    [PurpleBooth](https://github.com/PurpleBooth)
