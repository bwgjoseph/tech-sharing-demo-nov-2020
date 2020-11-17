import { HookContext } from '@feathersjs/feathers';
import { Application } from '../../../declarations';

const createComment = async (context: HookContext) => {

  const { data, app, params } = context;

  const commentsPromise = data.comments?.map(async (comment: string) => {
    (app as Application)
      .service('comments')
      .create({
        text: comment,
      }, params.forward());
  });

  if (commentsPromise) {
    await Promise.all(commentsPromise);
  }

  delete context.data.comments;

  context.data.createdBy = params.user?.email;

  return context;
};

export default createComment;
