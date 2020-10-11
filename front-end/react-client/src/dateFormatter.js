export const dateConverter = oldDate => {
  let newDate = new Date(oldDate);

  //console.log(dd.toLocaleDateString("en-GB"))
  return newDate.toLocaleDateString("en-GB");
};
