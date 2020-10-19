export const dateConverter = oldDate => {
  let newDate = new Date(oldDate);

  return newDate.toLocaleDateString("en-GB");
};
