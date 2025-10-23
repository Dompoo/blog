- 메타가 개발한 API용 쿼리 언어
- 기존 REST에서는 필요한 데이터에 비해 너무 많이 또는 적게 들어오는 문제가 있었다. (under/over fetching)
- 하지만 graphql에서는 정확히 원하는 데이터만 가져올 수 있다.
```text
query {
	user(id: "123") {
		name
		email
		posts {
			title 
		}
	}
}
```
- REST와 다르게 `/graphql` 단일 엔드포인트만 열어두면 된다.
- 스키마를 통해 API의 모든 타입과 관계를 명확히 정의해놓는다.
```text
type User {
	id: ID!
	name: String!
	email: String!
	posts: [Post!]!
}

type Post {
	id: ID!
	title: String!
	content: String!
	author: User!
}
```
- 대신에 graphql은 쿼리가 매번 다르기 때문에 캐싱하기 어렵다.