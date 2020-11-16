interface Post {
    readonly _id: string;
    title: string;
    body: string;
    comments?: string[];
    readonly createdBy: string;
}

export default Post;
